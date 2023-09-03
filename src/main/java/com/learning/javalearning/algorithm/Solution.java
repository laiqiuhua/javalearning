package com.learning.javalearning.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 */
public class Solution {
    int row;
    Integer[][] memo;

    public int minimumTotal(List<List<Integer>> triangle) {
        row = triangle.size();
        memo = new Integer[row][row];
        return helper(0,0, triangle);
    }

    private int helper(int level, int c, List<List<Integer>> triangle){
        if (memo[level][c]!=null)
            return memo[level][c];
        if (level==row-1){
            return memo[level][c] = triangle.get(level).get(c);
        }
        System.out.println("left: "+ level+ "   c:"+ c);
        System.out.println("right: "+ level + "   c:"+ (c+1));
        System.out.println("result:"+triangle.get(level).get(c));
        int left = helper(level+1, c, triangle);
        int right = helper(level+1, c+1, triangle);
        return memo[level][c] = Math.min(left, right) + triangle.get(level).get(c);
    }

    public static void main(String[] args) {
       Integer[][]  input = {
           {2},
          {3, 4},
         {6, 5, 7},
        {4, 1, 8, 3}
        };

        List<List<Integer>> arr = Arrays.stream(input)
                .map(Arrays::asList)
                .collect(Collectors.toList());
       int result = new Solution().minimumTotal(arr);
        System.out.println(result);
    }
}