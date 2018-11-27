package com.adeveloperh.androidreversestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.adeveloperh.androidreversestudy.jni.HelloJNIActivity;
import com.adeveloperh.androidreversestudy.lockpwd.LockPwdActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lockpwd(View view) {
        startActivity(new Intent(this, LockPwdActivity.class));
    }

    public void jni(View view) {
        startActivity(new Intent(this, HelloJNIActivity.class));
    }
}
