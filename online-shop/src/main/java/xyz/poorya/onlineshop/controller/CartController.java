package xyz.poorya.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.exceptions.CartException;
import xyz.poorya.onlineshop.service.CartService;

import java.security.Principal;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    //++++++++++++++
    @PostMapping(value = "/api/products/{id}/addtocart/{quantity}")
    public ResponseEntity<ResponseDto> addTocart(@PathVariable String id, @PathVariable int quantity, Principal principal) {
        cartService.addToCart(id, quantity, principal.getName());
        return new ResponseEntity<ResponseDto>(new ResponseDto("Item added Successfully"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ResponseDto> return400(CartException ex) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }
}
