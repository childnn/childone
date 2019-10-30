package com.example.mock.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/7 11:28
 * 限定: {@link Region}注解的字段只能是China、China-Taiwan、China-HongKong这三个中的一个。
 */
public class RegionValidator implements ConstraintValidator<Region, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        HashSet<String> regions = new HashSet<>();
        regions.add("China");
        regions.add("China-Taiwan");
        regions.add("China-HongKong");
        return regions.contains(value);
    }
}
