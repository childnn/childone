package com.example.dateformat.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * 2019/8/9 14:15
 */
@Data
public class DateEntity {

    // 2019-12-12 23:24:24
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参格式化: 接收页面 字符串
    private Date date;
}
