package util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MiaoOne
 * 2019/8/7 17:17
 */
public final class CombinationUtil {

    /**
     * 递归排列组合多个数组的元素: 笛卡尔积
     *
     * @param dataList   数据源: 包含了所有待排列组合的数组的集合 -- 一个数组表示一个同类别的属性的所有值(每一个都会与其他数组的所有值排列组合).
     * @param index      从 dataList 的第几号索引开始排列组合
     * @param resultList 存放已经排列好的数组的集合
     * @return resultList
     */
    public static List<Object[]> combination(List<Object[]> dataList, int index, List<Object[]> resultList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException(": " + index);
        }
        if (index == dataList.size()) {
            return resultList;
        }

        // 构造 存放数组的新的集合.
        List<Object[]> resultList0 = new ArrayList<>(); // 每次递归, 都构造新的集合, 存放新的结果集.

        Object[] srcArr = dataList.get(index);
        if (index == 0) {
            for (Object obj : srcArr) {
                // 把 数据源的 0 号索引(第一个 数组)的每一个元素放入新的数组, 并放入集合.
                resultList0.add(new Object[]{obj});
            }
        } else {
            // ...
            if (CollectionUtils.isEmpty(resultList)) {
                return Collections.emptyList();
            }
            // 获取 数据源 指定索引的 元素(数组).
            // Object[] srcArr = dataList.get(index);

            // 将 resultList 的每一个元素(数组), 与 待拼接数组的每一个元素 排列组合.
            // 这里的 resultList 就是上一次 存放了每一个数组的集合.
            // 获取 resultList 中的每一个组合好的数组.
            for (Object[] resultArr : resultList) {
                // 获取未参与组合的数组中每个元素, 组合到已经组合过的数组之后.
                for (Object obj : srcArr) {
                    //复制数组并扩充新元素.
                    // resultArr 长度加 1, 往后追加一个元素.
                    Object[] newArr = new Object[resultArr.length + 1]; // 每一个内层循环构造一个新的长度加一的数组, 把数据源数组的元素追加在新数组最后.
                    // 把 resultArr 的每一个元素复制到 新数组(长度加一).
                    System.arraycopy(resultArr, 0, newArr, 0, resultArr.length);
                    // 将 数据源的元素赋值给 新数组.
                    newArr[newArr.length - 1] = obj; // 追加.

                    // 把得到的新数组加到结果集.
                    resultList0.add(newArr);
                }
            }
        }
        // 递归
        return combination(dataList, ++index, resultList0);
    }

}
