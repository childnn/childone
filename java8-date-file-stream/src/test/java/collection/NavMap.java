package collection;

import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static collection.HTMLColors.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 17:16
 * 由 **TreeMap** 和 **ConcurrentSkipListMap** 实现的 **NavigableMap** 接口解决了需要选择Map片段的问题。
 */
public class NavMap {
    public static final NavigableMap<Integer, String> COLORS = new ConcurrentSkipListMap<>(MAP);

    public static void main(String[] args) {
        show(COLORS.firstEntry());
        border();
        show(COLORS.lastEntry());
        border();
        NavigableMap<Integer, String> toLime =
                COLORS.headMap(rgb("Lime"), true);
        show(toLime);
        border();
        show(COLORS.ceilingEntry(rgb("DeepSkyBlue") - 1));
        border();
        show(COLORS.floorEntry(rgb("DeepSkyBlue") - 1));
        border();
        show(toLime.descendingMap());
        border();
        show(COLORS.tailMap(rgb("MistyRose"), true));
        border();
        show(COLORS.subMap(
                rgb("Orchid"), true,
                rgb("DarkSalmon"), false));
    }
}
