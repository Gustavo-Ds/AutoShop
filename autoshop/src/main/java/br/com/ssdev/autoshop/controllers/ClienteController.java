package br.com.ssdev.autoshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ClienteController {
    @GetMapping("/helloworld")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld(){
        return "Hello World !!!";
    }

}
