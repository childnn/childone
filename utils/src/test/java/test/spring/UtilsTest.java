package test.spring;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see StringUtils#addStringToArray(String[], String) // 追加 string.
 * @see StringUtils#applyRelativePath(String, String)
 * @see StringUtils#arrayToCommaDelimitedString(Object[]) // 数组元素逗号分隔, 转字符串.
 * @see StringUtils#arrayToDelimitedString(Object[], String) // 指定分隔符, 数组转字符串.
 * @see StringUtils#capitalize(String) // 首字母转大写.
 * @see StringUtils#uncapitalize(String) // 首字母转小写.
 * private @see StringUtils#changeFirstCharacterCase(String, boolean)
 * @see StringUtils#cleanPath(String)
 * @see StringUtils#collectionToCommaDelimitedString(Collection)
 * @see StringUtils#collectionToDelimitedString(Collection, String)
 * @see StringUtils#collectionToDelimitedString(Collection, String, String, String)
 * @see StringUtils#commaDelimitedListToSet(String)
 * @see StringUtils#commaDelimitedListToStringArray(String)
 * @see StringUtils#concatenateStringArrays(String[], String[])
 * private @see StringUtils#containsText(CharSequence)
 * @see StringUtils#containsWhitespace(String)
 * @see StringUtils#containsWhitespace(CharSequence)
 * @see StringUtils#countOccurrencesOf(String, String)
 * @see StringUtils#delete(String, String)
 * @see StringUtils#deleteAny(String, String)
 * @see StringUtils#delimitedListToStringArray(String, String)
 * @see StringUtils#delimitedListToStringArray(String, String, String)
 * @see StringUtils#endsWithIgnoreCase(String, String)
 * @see StringUtils#getFilename(String)
 * @see StringUtils#getFilenameExtension(String)
 * @see StringUtils#hasLength(String)
 * @see StringUtils#hasLength(CharSequence)
 * @see StringUtils#hasText(String)
 * @see StringUtils#hasText(CharSequence)
 * @see StringUtils#isEmpty(Object)
 * @see StringUtils#mergeStringArrays(String[], String[])
 * @see StringUtils#parseLocale(String)
 * @see StringUtils#parseLocaleString(String)
 * private @see StringUtils#parseLocaleTokens(String, String[])
 * @see StringUtils#pathEquals(String, String)
 * @see StringUtils#quote(String)
 * @see StringUtils#quoteIfString(Object)
 * @see StringUtils#removeDuplicateStrings(String[])
 * @see StringUtils#replace(String, String, String)
 * @see StringUtils#sortStringArray(String[])
 * @see StringUtils#split(String, String)
 * @see StringUtils#splitArrayElementsIntoProperties(String[], String)
 * @see StringUtils#splitArrayElementsIntoProperties(String[], String, String)
 * @see StringUtils#startsWithIgnoreCase(String, String)
 * @see StringUtils#stripFilenameExtension(String)
 * @see StringUtils#substringMatch(CharSequence, int, CharSequence)
 * private @see StringUtils#tokenizeLocaleSource(String)
 * @see StringUtils#tokenizeToStringArray(String, String)
 * @see StringUtils#tokenizeToStringArray(String, String, boolean, boolean)
 * @see StringUtils#toLanguageTag(Locale)
 * @see StringUtils#toStringArray(Collection)
 * @see StringUtils#toStringArray(Enumeration)
 * @see StringUtils#trimAllWhitespace(String)
 * @see StringUtils#trimArrayElements(String[])
 * @see StringUtils#trimLeadingCharacter(String, char)
 * @see StringUtils#trimLeadingWhitespace(String)
 * @see StringUtils#trimTrailingCharacter(String, char)
 * @see StringUtils#trimTrailingWhitespace(String)
 * @see StringUtils#trimWhitespace(String)
 * @see StringUtils#unqualify(String)
 * @see StringUtils#unqualify(String, char)
 * @see StringUtils#uriDecode(String, Charset)
 * private @see StringUtils#validateLocalePart(String)
 * @since 2019/9/29 13:44
 */
public class UtilsTest {
    @Test
    public void test1() {
        String[] arr = {"java", "demo"};
        String[] strings = StringUtils.addStringToArray(arr, "test");
        String s = Arrays.toString(strings);
        System.out.println("s = " + s);
    }

    @Test
    public void test2() {
        // 舍弃最后一个分隔符 "/" 后的路径。
        String s = StringUtils.applyRelativePath("d:/develop/java/", "/test/demo");
        String s1 = StringUtils.applyRelativePath("d:/develop/java", "/test/demo");
        System.out.println("s = " + s); // d:/develop/java/test/demo
        System.out.println("s1 = " + s1); // d:/develop/test/demo
    }

    /**
     * org.apache.commons.lang3.StringUtils#join(java.lang.Object[], char)
     */
    @Test
    public void test3() {
        Object[] arr = {1, 2, 3, 4};
        String s = StringUtils.arrayToCommaDelimitedString(arr);
        System.out.println("s = " + s);
        String s1 = StringUtils.arrayToDelimitedString(arr, "-");
        System.out.println("s1 = " + s1);
    }

    @Test
    public void test4() {
        String java = StringUtils.capitalize("java");
        System.out.println("java = " + java);
        String s = StringUtils.uncapitalize(java);
        System.out.println("s = " + s);
    }
}
