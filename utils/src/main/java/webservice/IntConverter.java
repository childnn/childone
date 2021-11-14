package webservice;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/19 10:41
 */
public class IntConverter implements JsonSerializer<Integer>,
        JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (StringUtils.isBlank(json.getAsString())) {
            return 0;
        }
        return Integer.parseInt(json.getAsString());
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive(0);
        }
        return new JsonPrimitive(src);
    }
}
