package xyz.poorya.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import xyz.poorya.onlineshop.domain.User.Location;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.dto.UserDto;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.RoleRepository;
import xyz.poorya.onlineshop.security.TokenService;
import xyz.poorya.onlineshop.service.UserService;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers =AuthController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class} )
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private RabbitMQSender rabbitMQSender;


    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void shouldCreateTutorial() throws Exception {
        Role role_user = new Role("USER");

        Mockito.when(userService.createUser(any(UserDto.class),any(List.class))).thenReturn(null);
        Mockito.when(roleRepository.findByName("USER")).thenReturn(Optional.of(role_user));

        UserDto userDto = UserDto.builder()
                .username("poorya")
                .fName("Poorya")
                .lName("Mohammadi")
                .email("pm1375pm@gmail.com")
                .password("poorya1997poorya*&")
                .phoneNumber("+98 9367518767")
                .location(new Location("Yazd", "UK", 180D, 190D))
                .build();


        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
