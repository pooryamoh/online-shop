package xyz.poorya.onlineshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.dto.UserWithoutUsernameDto;
import xyz.poorya.onlineshop.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/users/{id}")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserWithoutUsernameDto userWithoutUsernameDto, @PathVariable String userID, Principal principal) {
        if (!userService.userHasAccess(userID, principal)) {
            return new ResponseEntity<ResponseDto>(new ResponseDto("You don't have access"), HttpStatus.UNAUTHORIZED);
        }
        userService.updateUser(userWithoutUsernameDto, userID);

        return new ResponseEntity<ResponseDto>(new ResponseDto("User updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String userID, Principal principal) {
        if (!userService.userHasAccess(userID, principal)) {
            return new ResponseEntity<ResponseDto>(new ResponseDto("You don't have access"), HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(userID);

        return new ResponseEntity<ResponseDto>(new ResponseDto("User deleted successfully"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> return400(UsernameNotFoundException ex) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }
}
