package com.adeveloperh.androidreversestudy.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adeveloperh.androidreversestudy.R;
import com.adeveloperh.androidreversestudy.jni.book.Hello;
import com.adeveloperh.androidreversestudy.jni.book.JNIDemo;

public class HelloJNIActivity extends AppCompatActivity {
    static {
        System.loadLibrary("helloJNI");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);

//        JNIUtils.sayHello();
//
//        Log.d("hj", "HelloJNIActivity.onCreate: " + JNIUtils.getStrFromJNI());
//        Log.d("hj", "HelloJNIActivity.onCreate: " + JNIUtils.getSum(2, 3));
//
//        Person person = new Person();
//        person.setName("huangjian");
//        person.setAge(27);
//        JNIUtils.printPersonMsg(person);
//
//        JNIUtils.invokeJavaStaticMethod();
//
//        Person invokeMethodPerson = new Person();
//        invokeMethodPerson.invokeJavaMethod();
//        Log.d("hj", "HelloJNIActivity.onCreate: person:" + person.toString());

//        Hello hello = new Hello();
//        hello.test();

        JNIDemo jniDemo = new JNIDemo();
        jniDemo.sayHello();
        System.out.println(jniDemo.getNumber());

        jniDemo.createJavaObject();
    }
}
