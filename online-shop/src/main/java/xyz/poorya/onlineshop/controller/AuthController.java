package xyz.poorya.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.dto.AuthResponseDto;
import xyz.poorya.onlineshop.dto.LoginDto;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.dto.UserDto;
import xyz.poorya.onlineshop.exceptions.UserNameExistsException;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.RoleRepository;
import xyz.poorya.onlineshop.security.TokenService;
import xyz.poorya.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    RabbitMQSender rabbitMQSender;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        String token = tokenService.getToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

    }

    @PostMapping("register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDto) {

        Optional<Role> roleByName = roleRepository.findByName("USER");
        Role role = roleByName.get();
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);

        userService.createUser(userDto, roles);


        return new ResponseEntity<ResponseDto>(new ResponseDto("User registered successfully!"), HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNameExistsException.class)
    public String return400(UserNameExistsException ex) {
        return ex.getMessage();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> return400(IllegalArgumentException ex) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

}
