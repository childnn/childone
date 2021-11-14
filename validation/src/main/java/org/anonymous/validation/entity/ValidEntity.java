package org.anonymous.validation.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/16 13:45
 */
public class ValidEntity {

    @NotBlank(message = "name can't be empty")
    private String name;

    // javax.validation.UnexpectedTypeException: HV000030:
    // No validator could be found for constraint 'javax.validation.constraints.NotBlank'
    // validating type 'java.math.BigDecimal'. Check configuration for 'bigDecimal'
    @NotBlank(message = "bigDecimal can't be empty")
    private BigDecimal bigDecimal;

    @NotEmpty(message = "Not empty")
    private List<String> list;
}
