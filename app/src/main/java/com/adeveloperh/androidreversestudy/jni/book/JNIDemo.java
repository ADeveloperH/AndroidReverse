package com.adeveloperh.androidreversestudy.jni.book;

/**
 * @author huangjian
 * @create 2018/11/30
 * @Description
 */
public class JNIDemo {
    private int number = 0;

    public String msg = null;

    //多态机制
    Father father = new Child();


    public native void sayHello();

    public native void createJavaObject();

    public int getNumber() {
        return number;
    }

    private boolean function(int a, double b, char c) {
        return a + b < c;
    }
}
