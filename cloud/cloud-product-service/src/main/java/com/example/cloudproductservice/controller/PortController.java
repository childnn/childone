package com.example.cloudproductservice.controller;

import com.example.cloudproductservice.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 19:56
 */
@RestController
public class PortController {

    @Autowired
    private PortService portService;

    @RequestMapping("ports")
    public Object ports() {
        return portService.ports();
    }
}
