package jackson.pojo.serial.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Student {
    @JsonIgnore // 忽略
    private Integer id;
    private String name;
    private String sex;
}
