package com.example.dateformat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * 2019/8/9 14:24
 * @see DateTimeFormat 入参格式化: string --> Date
 * @see JsonFormat 出参格式化: 用在日期上时, 相当于 {@link java.text.SimpleDateFormat}
 */
@Data // 必须要 set 方法
public class DateVo {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 入参格式化: 将指定 pattern 格式的 字符串 封装到 Date 类型
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 出参格式化: 将 Date 类型日期 格式化为 指定的 pattern 类型
    private Date date;
}
