package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/21 11:51
 * given an array of integers, return indices of the two numbers such that
 * they add up to specific target.
 * you may assume that each input would have exactly one solution,
 * and you may not use the same element twice.
 * example:
 * given nums = [2, 7, 11, 15], target = 9
 * because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    /**
     * 方法一:
     * 注意获取数组中的数,在剩余的数中找到与其和为 target 的数.
     * 时间复杂度 O(n^2), 空间复杂度 O(1).
     *
     * @param nums
     * @param target
     * @return
     */
    static int[] twoIndicesOfTwoSum1(int[] nums, int target) {
        int[] result = new int[2];
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            // 可能为负值
            int another = target - nums[i];
            for (int j = i + 1; j < length; j++) {
                if (another == nums[j]) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 方法二: 时间复杂度/空间复杂度 O(n)
     * map<num, index>
     *
     * @param nums
     * @param target
     * @return
     */
    static int[] twoIndicesOfTwoSum2(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int sub = target - num;
            if (map.containsKey(sub)) {
                result[1] = i;
                result[0] = map.get(sub);
                return result;
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        toString(twoIndicesOfTwoSum1(new int[]{2, 7, 11, 15}, 9));
        toString(twoIndicesOfTwoSum1(new int[]{}, 1));
        toString(twoIndicesOfTwoSum1(new int[]{1, 3, -1, 2}, 5));
        toString(twoIndicesOfTwoSum1(new int[]{1, 3, -1, 2}, 0));
        System.out.println("=================================");
        toString(twoIndicesOfTwoSum2(new int[]{2, 7, 11, 15}, 9));
        toString(twoIndicesOfTwoSum2(new int[]{}, 1));
        toString(twoIndicesOfTwoSum2(new int[]{1, 3, -1, 2}, 5));
        toString(twoIndicesOfTwoSum2(new int[]{1, 3, -1, 2}, 0));
    }

    public static void toString(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}
