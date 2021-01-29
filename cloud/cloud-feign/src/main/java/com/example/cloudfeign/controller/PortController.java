package com.example.cloudfeign.controller;

import com.example.cloudfeign.entity.PortEntity;
import com.example.cloudfeign.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 20:11
 */
@Controller
public class PortController {

    @Autowired
    private PortService portService;

    @RequestMapping("/ports")
    public String ports(Model m) {
        List<PortEntity> ports = portService.ports();
        m.addAttribute("ps", ports);
        return "products";
    }

}
