package com.example.bootactuator.beans;

import javax.validation.constraints.NotBlank;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/16 11:46
 */
// @Data
public class ValidReq {

    @NotBlank(message = "name should not blank!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
