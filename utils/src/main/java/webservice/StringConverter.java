package webservice;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/19 0:05
 */
public class StringConverter implements JsonSerializer<String>,
        JsonDeserializer<String> {
    public JsonElement serialize(String src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src);
        }
    }

    public String deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}
