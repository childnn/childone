package org.anonymous.nacos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/17 11:27
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestConfigEntity {

    private LocalDateTime dateTime;

}
