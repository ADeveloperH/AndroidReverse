package com.adeveloperh.androidreversestudy.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adeveloperh.androidreversestudy.R;
import com.adeveloperh.androidreversestudy.jni.bean.Person;

public class HelloJNIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);

        JNIUtils.sayHello();

        Log.d("hj", "HelloJNIActivity.onCreate: " + JNIUtils.getStrFromJNI());
        Log.d("hj", "HelloJNIActivity.onCreate: " + JNIUtils.getSum(2, 3));

        Person person = new Person();
        person.setName("huangjian");
        person.setAge(27);
        JNIUtils.printPersonMsg(person);

        JNIUtils.invokeJavaStaticMethod();
    }
}
