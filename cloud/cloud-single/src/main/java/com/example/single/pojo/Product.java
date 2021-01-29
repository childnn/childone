package com.example.single.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String name;

    private int id;

    private float price;

}
