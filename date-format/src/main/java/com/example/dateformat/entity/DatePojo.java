package com.example.dateformat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/16 14:28
 */
@Data
public class DatePojo {
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 出参格式化
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 出参无效
    private Date date;
}
