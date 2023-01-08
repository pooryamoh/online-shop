package xyz.poorya.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.poorya.onlineshop.dto.ResponseDto;
import xyz.poorya.onlineshop.service.CartService;
import xyz.poorya.onlineshop.service.LoadData;

import java.security.Principal;

@RestController
public class LoadDataController {
    @Autowired
    private LoadData loadData;

    //++++++++++++++
    @GetMapping(value = "/api/load")
    public ResponseEntity<ResponseDto> load() {
        loadData.loadData();
        return new ResponseEntity<ResponseDto>(new ResponseDto("Data Loaded Successfully"), HttpStatus.OK);
    }
}
