package com.example.cloudproductservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 19:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortEntity {

    private int port;

    private String name;

    private String profile;

}
