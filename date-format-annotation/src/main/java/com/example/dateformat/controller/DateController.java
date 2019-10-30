package com.example.dateformat.controller;

import com.example.dateformat.entity.DateEntity;
import com.example.dateformat.entity.DatePojo;
import com.example.dateformat.entity.DateVo;
import com.example.dateformat.entity.ListDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * 2019/8/9 14:13
 */
@RestController
//@RequestMapping("/hello")
public class DateController {

    /**
     * 使用 {@link DateTimeFormat} 指定 前端传递的 日期字符串的格式, 可以接受 字符串, 但是转化后的结果还是
     * {@link Date} 的默认格式 -- Fri Aug 09 14:24:13 CST 2019
     *
     * @param entity com.example.mybatispluschild.entity
     * @return 返回的日期格式不是格式化过的, 还是 {@link Date} 的默认格式
     *
     * @see DateTimeFormat 只负责 入参 字符串转化为 {@code Date} 类型
     */
    @GetMapping("test")
    public DateEntity date(DateEntity entity) {
        System.out.println("com.example.mybatispluschild.entity = " + entity); // Thu Aug 02 22:05:55 CST 2018
        System.out.println("new Date() = " + new Date()); // Fri Aug 09 14:24:13 CST 2019
        return entity;
    }

    /**
     * @param vo vo
     * @return 返回的是格式化过的 {@code Date}
     *
     * @see JsonFormat#pattern()  将 {@link Date} 格式化为指定的 格式 输出
     */
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @RequestMapping("vo")
    public DateVo date(DateVo vo) {
        System.out.println("vo = " + vo);
        return vo;
    }

    /**
     * 使用 {@link JsonFormat} 注解 无法接收页面字符串形式的 日期格式
     *
     * @return 出参格式化 Date to String
     */
    @PostMapping("/pojo")
    public DatePojo jsonFormat(/*DatePojo pojo*/) {
        // System.out.println("pojo.getDate() = " + pojo.getDate());
        System.out.println("new Date() = " + new Date());
        DatePojo pojo = new DatePojo();
        pojo.setDate(new Date());
        return pojo;
    }

    @GetMapping("list")
    public ListDate list(ListDate list) {
        list.getDateList().forEach(System.err::println);
        return list;
    }
}
