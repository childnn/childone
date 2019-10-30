package com.example.dateformat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/16 14:50
 */
@Data
public class ListDate {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参: 指定输入格式
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss") // 出参: 指定输出格式
    private List<Date> dateList;
}
