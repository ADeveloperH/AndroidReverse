package com.adeveloperh.androidreversestudy.lockpwd;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adeveloperh.androidreversestudy.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LockPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pwd);
    }

    public void getsalt(View view) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
            Object lockUtils = clazz.getConstructor(Context.class).newInstance(this);
            Class<?> lockUtilsClass = lockUtils.getClass();
            Method getSaltMethod = lockUtilsClass.getDeclaredMethod("getSalt", int.class);
            getSaltMethod.setAccessible(true);
            Object salt = getSaltMethod.invoke(lockUtils, 0);
            Log.d("hj", "LockPwdActivity.getsalt: " + salt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
