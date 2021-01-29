package com.example.single.service;

import com.example.single.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 15:24
 */
@Service
public class ProductService {

    public List<Product> list() {
        return Arrays.asList(new Product("Prod-a", 1, 50f),
                new Product("Prod-b", 2, 30f),
                new Product("Prod-c", 3, 33f));
    }
}
