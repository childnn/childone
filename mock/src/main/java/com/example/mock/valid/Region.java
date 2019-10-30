package com.example.mock.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/7 11:27
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegionValidator.class)
public @interface Region {

    String message() default "Region 值不在可选范围内";

    /**
     * 某些场景下我们需要使用到验证组, 说简单点就是对对象操作的不同方法有不同的验证规则.
     * -------------------------------------------------------
     * 先创建两个接口：
     * public interface AddPersonGroup {
     * }
     * public interface DeletePersonGroup {
     * }
     * -------------------------------------------------------
     *
     * @NotNull(groups = DeletePersonGroup.class)
     * @Null(groups = AddPersonGroup.class)
     * private String group;
     * @Service
     * @Validated public class PersonService {
     * <p>
     * public void validatePerson(@Valid Person person) {
     * // do something
     * }
     * @Validated(AddPersonGroup.class) public void validatePersonGroupForAdd(@Valid Person person) {
     * // do something
     * }
     * @Validated(DeletePersonGroup.class) public void validatePersonGroupForDelete(@Valid Person person) {
     * // do something
     * }
     * <p>
     * }
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
