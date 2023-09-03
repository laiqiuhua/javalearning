package com.learning.javalearning.leetcode;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int first = 0, last = nums.length - 1; first <= last; first++, last--) {

            int another1 = target - nums[first];
            int another2 = target - nums[last];
            if (map.containsKey(another1)) {
                result[0] = first;
                result[1] = map.get(another1);
                break;
            }
            map.put(nums[first], first);

            if (map.containsKey(another2)) {
                result[0] = last;
                result[1] = map.get(another2);
                break;
            }
            map.put(nums[last], last);
        }
        return result;
    }
}