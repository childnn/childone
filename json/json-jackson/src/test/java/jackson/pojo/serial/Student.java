package jackson.pojo.serial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:00
 */
@Data
@Accessors(chain = true)
// @JsonIgnoreType // 被其他类引用时, 忽略.
@JsonIgnoreProperties(value = {"name"})
public class Student {
    // @JsonIgnore // 忽略
    private int id;
    private String name;
    private String sex;
}
