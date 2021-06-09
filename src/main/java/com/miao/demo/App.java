package com.miao.demo;

import com.miao.demo.entity.Person;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args){
        System.out.println("按数字键 1 调用测试方法");
        while (true) {
            Scanner reader = new Scanner(System.in);
            int number = reader.nextInt();
            if(number==1){
                Person person = new Person();
                person.test();
            }
        }
    }
}
