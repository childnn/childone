package webservice;

import com.google.gson.*;
import util.Java8DateUtil;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/19 0:07
 */
public class DateConverter implements JsonSerializer<Date>,
        JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Date();
    }

    public static void main(String[] args) {
        // System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.toFormat().format(new Date()));
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(Java8DateUtil.date2LocalDateTime(new Date()))

                    /*Java8DateUtil.format(new Date(), () -> "yyyy-MM-dd'T'HH:mm:ss")*/);
        } else {
            return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(Java8DateUtil.date2LocalDateTime(src))
                    /*Java8DateUtil.format(src, () -> "yyyy-MM-ddTHH:mm:ss")*/)/*Java8DateUtil.formatToDateTime(src))*/;
        }
    }
}
