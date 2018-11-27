//
// Created by bignox on 2018/11/27.
//
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
extern "C"

JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_invokeJavaStaticMethod
        (JNIEnv *env, jclass clazz) {
    jmethodID methodID = env->GetStaticMethodID(clazz, "staticMethodFromJava", "()I");
    jint result = env->CallStaticIntMethod(clazz, methodID);
    LOGD("获取的值是 ：%d" , result);
};