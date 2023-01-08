package xyz.poorya.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.poorya.onlineshop.domain.Transaction;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.dto.TransactionHTTPResponseDto;
import xyz.poorya.onlineshop.exceptions.CartException;
import xyz.poorya.onlineshop.service.TransactionService;

import java.security.Principal;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Value("${hostname}")
    private String hostname;
    @PostMapping(value = "/api/paycart")
    public ResponseEntity<TransactionHTTPResponseDto> addTocart(Principal principal) {
        Transaction transaction = transactionService.payCart(principal.getName());
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        String url = builder.scheme("http").host(hostname).path("/api").path("/transactions").path("/" + transaction.getId()).toUriString();
        return new ResponseEntity<TransactionHTTPResponseDto>(new TransactionHTTPResponseDto(url), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ResponseDto> return400(CartException ex) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

}
