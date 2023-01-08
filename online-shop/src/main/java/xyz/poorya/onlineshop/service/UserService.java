package xyz.poorya.onlineshop.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.domain.User.UserEntity;
import xyz.poorya.onlineshop.dto.UserDto;
import xyz.poorya.onlineshop.dto.UserQueueDto;
import xyz.poorya.onlineshop.dto.UserWithoutUsernameDto;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.RoleRepository;
import xyz.poorya.onlineshop.repo.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private RabbitMQSender rabbitMQSender;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RabbitMQSender rabbitMQSender,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rabbitMQSender = rabbitMQSender;
        this.roleRepository = roleRepository;
    }

    public static boolean patternMatches(String input, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(input)
                .matches();
    }

    public UserEntity createUser(UserDto userDto, List<Role> role) {
        if (userDto.isNull()) {
            throw new IllegalArgumentException("Some fields are empty");
        }

        emailValidation(userDto.getEmail());
        passwordValidation(userDto.getPassword());
        usernameValidation(userDto.getUsername());
        phoneValidation(userDto.getPhoneNumber());
        countryValidation(userDto.getLocation().getCountry());

        UserEntity userEntity = UserEntity
                .builder()
                .fName(userDto.getFName())
                .lName(userDto.getLName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .location(userDto.getLocation())
                .role(role)
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        UserQueueDto userQueueDto = UserQueueDto
                .builder()
                .username(savedUser.getUsername())
                .lName(savedUser.getLName())
                .fName(savedUser.getFName())
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .build();
        rabbitMQSender.send(userQueueDto);

        return savedUser;
    }

    public UserEntity updateUser(UserWithoutUsernameDto userWithoutUsernameDto, String userId) {
        if (userWithoutUsernameDto.isNull()) {
            throw new IllegalArgumentException("Some fields are empty");
        }

        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User id doesn't match with any user");
        }

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        UserEntity user = userEntity.get();

        emailValidation(userWithoutUsernameDto.getEmail());
        passwordValidation(userWithoutUsernameDto.getPassword());
        phoneValidation(userWithoutUsernameDto.getPhoneNumber());
        countryValidation(userWithoutUsernameDto.getLocation().getCountry());

        user.setEmail(userWithoutUsernameDto.getEmail());
        user.setLocation(userWithoutUsernameDto.getLocation());
        user.setFName(userWithoutUsernameDto.getFName());
        user.setLName(userWithoutUsernameDto.getLName());
        user.setPhoneNumber(userWithoutUsernameDto.getPhoneNumber());

        UserEntity savedUser = userRepository.save(user);

        UserQueueDto userQueueDto = UserQueueDto
                .builder()
                .username(savedUser.getUsername())
                .lName(savedUser.getLName())
                .fName(savedUser.getFName())
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .build();
        rabbitMQSender.send(userQueueDto);

        return savedUser;
    }

    public boolean userHasAccess(String id, Principal principal) {
        Optional<UserEntity> userByUsername = userRepository.findByUsername(principal.getName());
        UserEntity user = userByUsername.get();
        Optional<Role> roleByName = roleRepository.findByName("ADMIN");
        Role role = roleByName.get();
        if (user.getRole().contains(role) || user.getId().equals(id)) {
            return true;
        }
        return false;
    }

    public void deleteUser(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User id doesn't match with any user");
        }

        userRepository.deleteById(userId);
    }

    private void emailValidation(String email) {
        if (!patternMatches(email, "^(.+)@(\\S+)$")) {
            throw new IllegalArgumentException("Email is not valid");
        }
    }

    private void phoneValidation(String phone) {
        if (!patternMatches(phone, "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$")) {
            throw new IllegalArgumentException("Phone number is not valid");
        }
    }

    private void countryValidation(String country) {
        String upperCase = country.toUpperCase();
        if (!patternMatches(country, "US|UK|GERMANY")) {
            throw new IllegalArgumentException("Country is not valid");
        }
    }

    private void passwordValidation(String password) {
        if (!patternMatches(password, "^(?=.*?[a-zA-Z0-9])(?=.*[#?!@$%^&*-]{2,}).{10,}$")) {
            throw new IllegalArgumentException("Password is not valid (Should be at least 10 character and contains at least 2 special character)");
        }
    }


    private void usernameValidation(String username) {
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher m = special.matcher(username);
        if (m.find()) {
            throw new IllegalArgumentException("Username can't contain special characters");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }


}
