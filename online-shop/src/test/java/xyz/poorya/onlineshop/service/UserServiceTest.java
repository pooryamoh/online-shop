package xyz.poorya.onlineshop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.poorya.onlineshop.domain.User.Location;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.domain.User.UserEntity;
import xyz.poorya.onlineshop.dto.UserDto;
import xyz.poorya.onlineshop.dto.UserQueueDto;
import xyz.poorya.onlineshop.dto.UserWithoutUsernameDto;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.RoleRepository;
import xyz.poorya.onlineshop.repo.UserRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@DataMongoTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RabbitMQSender rabbitMQSender;
    @Mock
    private RoleRepository roleRepository;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void Should_Save_User_When_Create_User_Called() {
        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());
        Mockito.doNothing().when(rabbitMQSender).send(any(UserQueueDto.class));
        Mockito.when(userRepository.save(any(UserEntity.class))).then(returnsFirstArg());
        Mockito.when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("password");


        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .password("poorya1997poorya*&")
                .build();

        assertThat(userService.createUser(userDto, roles)).isNotNull();
    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_And_Some_Fields_Are_Empty() {

        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .password("poorya1997poorya*&")
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Some fields are empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_With_Weak_Password() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());


        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .password("fdfsg*&")
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Password is not valid (Should be at least 10 character and contains at least 2 special character)";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_With_Wrong_Email_Format() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());


        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pmgmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Email is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_With_Wrong_Phone_Number_Format() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());


        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("111")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Phone number is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_With_Username_Contains_Special_Character() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());


        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poo&rya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Username can't contain special characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Create_User_Called_With_Username_That_Exists() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());
        Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto, roles);
        });
        String expectedMessage = "Username already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void Should_Throw_Exception_When_Update_User_Called_With_Username_That_Not_Exists() {

        //Arrange
        Mockito.when(roleRepository.save(any(Role.class))).then(returnsFirstArg());
        Mockito.when(userRepository.existsById(any(String.class))).thenReturn(false);

        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);


        UserWithoutUsernameDto userDto = UserWithoutUsernameDto.builder()
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.updateUser(userDto, "123456");
        });
        String expectedMessage = "User id doesn't match with any user";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}
