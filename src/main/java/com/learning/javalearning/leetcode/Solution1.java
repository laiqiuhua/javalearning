package com.learning.javalearning.leetcode;

import java.util.HashMap;
import java.util.Map;

class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = nums.length - 1; j > 0; j--) {
                if (i != j && (nums[i] + nums[j]) == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    private String add(String a, String b) {

        String maxStr = a.length() >= b.length() ? a : b;
        String minStr = a.length() >= b.length() ? b : a;
        String sum = "";
        int maxL = maxL = maxStr.length();
        int[] max_Ints = new int[maxL];
        int[] min_Ints = new int[maxL];
        for (int i = 0; i < maxL; i++) {
            //运用“+""”的方法将字符型数字转化成整形数字
            max_Ints[i] = Integer.parseInt(maxStr.charAt(i) + "");
        }
        //对低位数字进行补零，使之与那个长的数字位数相同
        for (int i = 0; i < maxL; i++) {
            if (i < maxL - minStr.length()) {
                min_Ints[i] = 0;
            } else {
                min_Ints[i] = Integer.parseInt(
                        minStr.charAt(i - (maxStr.length() - minStr.length())) + "");
            }
        }
        for (int i = maxL - 1; i >= 0; i--) {
            int Esum = 0;//表示每位相加的和
            Esum = max_Ints[i] + min_Ints[i];
            if (Esum >= 10 && i > 0) {
                Esum = Esum % 10;
                min_Ints[i - 1] += 1;
            }
            sum = (Esum + "") + sum;
        }
        return sum;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        StringBuilder l1Str = new StringBuilder();
        StringBuilder l2Str = new StringBuilder();
        l1Str.append(l1.val);
        while (l1.next != null) {
            l1 = l1.next;
            int val = l1.val;
            l1Str.append(val);
        }
        l2Str.append(l2.val);
        while (l2.next != null) {
            l2 = l2.next;
            int val = l2.val;
            l2Str.append(val);
        }


        String resultStr = add(l1Str.reverse().toString(), l2Str.reverse().toString());
        String fstr = new StringBuffer(resultStr).reverse().toString();
        ListNode first = null;
        ListNode next = null;
        for (int i = 0; i < fstr.length(); i++) {
            int nodeValue = Integer.parseInt(String.valueOf(fstr.charAt(i)));
            if (i == 0) {
                first = new ListNode(nodeValue);
                next = first;
            } else {
                ListNode tmp = new ListNode(nodeValue);
                next.next = tmp;
                next = tmp;
            }
        }

        return first;
    }


    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    public int maxArea(int[] height) {
        int max = 0;
        int first = 0;
        int end = height.length-1;
        while(first<end){
            int minH =Math.min(height[first],height[end]);
            int s = (end-first) *minH ;
            max = Math.max(max,s);
            while(height[first]<=minH&&first<end){
                first++;
            }
            while(height[end]<=minH&&first<end){
                end--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
//        int[] input = {2, 5, 5, 11};
//        int[] result = new Solution1().twoSum(input, 10);
//        System.out.println(Arrays.toString(result));
//        ListNode l1 = new ListNode(2);
//        l1.next = new ListNode(4);
//        l1.next.next = new ListNode(3);
//
//        ListNode l2 = new ListNode(5);
//        l2.next = new ListNode(6);
//        l2.next.next = new ListNode(4);


        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);
        l2.next.next.next.next = new ListNode(9);
        l2.next.next.next.next.next = new ListNode(9);
        l2.next.next.next.next.next.next = new ListNode(9);
        l2.next.next.next.next.next.next.next = new ListNode(9);
        l2.next.next.next.next.next.next.next.next = new ListNode(9);
        l2.next.next.next.next.next.next.next.next.next = new ListNode(9);

//        ListNode result1 = new Solution1().addTwoNumbers(l1, l2);
//        System.out.println(result1);

//        int result1 = new Solution1().lengthOfLongestSubstring("abcabcabccdcdsdsfffsssvv");
//        System.out.println(result1);

        int[] height = new int[]{2,3,4,5,18,17,6};
//        int[] height = new int[] {1,1};
        int result1 = new Solution1().maxArea(height);
        System.out.println(result1);
    }


}