package com.example.bootactuator.controller;

import com.example.bootactuator.beans.ValidReq;
import com.example.bootactuator.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/25 16:02
 */
@RestController
public class PropController {

    @Autowired
    private MyProperties prop;

    @PostConstruct
    public void init() {
        System.out.println(prop.getAddress() + ": " + prop.getName());
    }

    @PostMapping("/valid")
    public String valid(@Valid/*ated*/ @RequestBody ValidReq req) {
        System.out.println("req = " + req.getName());
        return req.getName();
    }

}
