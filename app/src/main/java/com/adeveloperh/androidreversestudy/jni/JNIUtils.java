package com.adeveloperh.androidreversestudy.jni;

import com.adeveloperh.androidreversestudy.jni.bean.Person;

/**
 * @author huangjian
 * @create 2018/11/25
 * @Description
 */
public class JNIUtils {

    private static int count;

    public static native void sayHello();

    public static native String getStrFromJNI();

    public static native int getSum(int a, int b);

    public static native void printPersonMsg(Person person);

    public static int staticMethodFromJava() {
        count++;
        return count;
    }

    public static native void invokeJavaStaticMethod();
}
