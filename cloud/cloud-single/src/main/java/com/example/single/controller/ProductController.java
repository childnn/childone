package com.example.single.controller;

import com.example.single.pojo.Product;
import com.example.single.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 15:36
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @param m
     * @return
     * @see org.springframework.validation.support.BindingAwareModelMap
     */
    @RequestMapping("/list")
    public String list(Model m) {
        System.out.println(m);
        System.out.println(m.getClass());
        List<Product> list = productService.list();
        m.addAttribute("ps", list);
        return "products";
    }
}
