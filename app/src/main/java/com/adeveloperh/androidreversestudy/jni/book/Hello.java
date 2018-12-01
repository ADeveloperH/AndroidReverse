package com.adeveloperh.androidreversestudy.jni.book;

import java.util.Date;

/**
 * @author huangjian
 * @create 2018/11/30
 * @Description
 */
public class Hello {
    public int property;

    public int function(int foo, Date date, int[] arr) {
        System.out.println("function");
        return 1;
    }

    public native void test();
}
