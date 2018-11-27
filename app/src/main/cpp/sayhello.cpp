//
// Created by bignox on 2018/11/25.
//
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#ifdef __cplusplus
extern "C"
#endif


JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_sayHello
        (JNIEnv *env, jclass clazz) {
    LOGD("TAGD========================================测试一下================================%s", "helloJNI");
};
