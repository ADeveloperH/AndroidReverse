//
// Created by bignox on 2018/11/25.
//
#include <jni.h>
#ifdef __cplusplus
extern "C"
#endif

JNIEXPORT jstring JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_getStrFromJNI
        (JNIEnv * env, jclass clazz){

    return env->NewStringUTF("I am from JNI");
};