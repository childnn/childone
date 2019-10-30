package com.example.bootweb.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/5 11:42
 */
@Data
@Accessors(chain = true)
public class EmailTemplate {
    private Integer templateId;
    private String subject;
    private String content;
}
