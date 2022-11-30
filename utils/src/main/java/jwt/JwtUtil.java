package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/9/9 0:18
 */
public class JwtUtil {

    private static final String salt = "颜值";

    // payload 也可以是单个的 key-value
    public static String token(Map<String, Object> payload) {
        return JWT.create()
                .withPayload(payload)
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS)) // 过期时间
                .sign(Algorithm.HMAC256(salt)); // 签名: 盐
    }

    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(salt)).build().verify(token);
    }

    public static void main(String[] args) {
        String token = token(new HashMap<String, Object>() {{
            put("username", "jack");
            put("age", 12);
        }});
        System.out.println("token = " + token);

        DecodedJWT tokenInfo = getTokenInfo(token);
        String username = tokenInfo.getClaim("username").asString();
        System.out.println("username = " + username);
        Integer age = tokenInfo.getClaim("age").asInt();
        System.out.println("age = " + age);
    }

}
