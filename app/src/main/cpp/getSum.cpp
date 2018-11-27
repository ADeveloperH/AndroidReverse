//
// Created by bignox on 2018/11/25.
//

#include <jni.h>
extern "C"

JNIEXPORT jint JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_getSum
        (JNIEnv *env, jclass clazz, jint a, jint b) {
    return a + b;
}