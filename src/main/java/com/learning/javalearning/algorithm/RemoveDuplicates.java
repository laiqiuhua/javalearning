package com.learning.javalearning.algorithm;

import java.util.HashSet;
import java.util.Set;

// 一个链表节点
class Node {
    int data;
    Node next;

    Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }
}

public class RemoveDuplicates {
    // 打印给定链表的辅助函数
    public static void printList(Node head) {
        Node ptr = head;
        while (ptr != null) {
            System.out.print(ptr.data + " —> ");
            ptr = ptr.next;
        }
        System.out.println("null");
    }

    // 从排序列表中删除重复项的函数
    public static Node removeDuplicates(Node head) {
        Node previous = null;
        Node current = head;

        // 取一个空集来存储链表节点以供将来参考
        Set<Integer> set = new HashSet<>();

        // 直到链表为空
        while (current != null) {
            // 如果当前节点之前出现过，忽略它
            if (set.contains(current.data)) {
                previous.next = current.next;
            } else {
                // 将当前节点插入到集合中并继续下一个节点
                set.add(current.data);
                previous = current;
            }
            current = previous.next;
        }

        return head;
    }

    public static void main(String[] args) {
        // 输入键
        int[] keys = {5, 3, 4, 2, 5, 4, 1, 3};

        // 指向链表的头节点
        Node head = null;

        // 构造一个链表
        for (int i = keys.length - 1; i >= 0; i--) {
            head = new Node(keys[i], head);
        }

        removeDuplicates(head);

        // 打印链表
        printList(head);
    }
}