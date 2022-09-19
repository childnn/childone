package entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/17 22:38
 */
@Data
public class Address {
    private String city;

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(new ArrayList<>()));
    }

}
