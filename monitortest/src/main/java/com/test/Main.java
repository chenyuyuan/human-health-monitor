package com.test;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().trim();
        // 第一个stack存放整个字符串，用于最后出栈输出
        LinkedList<Character> stack1 = new LinkedList<>();
        // 第二个stack存放每一个括号里面需要重复的内容
        LinkedList<Character> stack2 = new LinkedList<>();
        // 第三个stack存放括号前面的数字
        LinkedList<Character> stack3 = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            char temp = str.charAt(i);
            // 遇到a-z,A-Z,0-9以及 (,[,{ 三种左括号直接入栈
            if ((temp >= 'A' && temp <= 'Z') || (temp >= 'a' && temp <= 'z') || (temp >= '0' && temp <= '9')) {
                stack1.push(temp);
            }
            if (temp == '(' || temp == '[' || temp == '{') {
                stack1.push(temp);
            }
            // 遇到右括号弹出括号内容以及前面可能存在的数字
            if (temp == ')' || temp == ']' || temp == '}') {
                if (temp == ')') {
                    while (stack1.getFirst() != '(') {
                        stack2.push(stack1.pop());
                    }
                }
                if (temp == ']') {
                    while (stack1.getFirst() != '[') {
                        stack2.push(stack1.pop());
                    }
                }
                if (temp == '}') {
                    while (stack1.getFirst() != '{') {
                        stack2.push(stack1.pop());
                    }
                }
                // 弹出左边的括号
                stack1.pop();
                // 弹出括号内容写入StringBuffer
                StringBuffer sb = new StringBuffer();
                while (!stack2.isEmpty()) {
                    sb.append(stack2.pop());
                }
                // 弹出左括号前面的数字，一定要判断 stack1 是不是为空！！！
                while (!stack1.isEmpty() && stack1.getFirst() >= '0' && stack1.getFirst() <= '9') {
                    stack3.push(stack1.pop());
                }
                if (!stack3.isEmpty()) {
                    StringBuffer sbNum = new StringBuffer();
                    while (!stack3.isEmpty()) {
                        sbNum.append(stack3.pop());
                    }
                    int count = Integer.parseInt(sbNum.toString());
                    StringBuffer add = new StringBuffer();
                    for (int j = 0; j < count; j++) {
                        add.append(sb.toString());
                    }
                    // 将括号里的内容复制count次，再压入到主栈 stack1 中
                    for (int k = 0; k < add.length(); k++) {
                        stack1.push(add.charAt(k));
                    }
                } else {
                    // 如果出现左边括号前没有数字的情况，如abc2{{A}},则把之前弹出的括号内的内容重新压入栈中
                    for (int j = 0; j < sb.length(); j++) {
                        stack1.push(sb.charAt(j));
                    }
                }

            }
        }
        while (!stack1.isEmpty()) {
            System.out.print(stack1.pop());
        }
        System.out.println();
    }
}
