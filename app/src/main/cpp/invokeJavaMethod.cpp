//
// Created by bignox on 2018/11/27.
//
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

/**
 * 千万注意：extern "C" 需要包含所有的 native 方法，否则会报错 No implementation found for
 *
 */
#ifdef __cplusplus
extern "C" {
#endif


/**
 *  区分一下下边两个方法的最后一个参数：
 *  方法1是静态方法：参数为 jclass 类型，CallStaticIntMethod 需要传入的也是 jclass 类型
 *  方法2是普通方法：参数为 jobject 类型，CallVoidMethod 需要传入的也是 jobject 类型
 *
 */


JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_invokeJavaStaticMethod
        (JNIEnv *env, jclass clazz) {
    jmethodID methodID = env->GetStaticMethodID(clazz, "staticMethodFromJava", "()I");
    jint result = env->CallStaticIntMethod(clazz, methodID);
    LOGD("获取的值是 ：%d", result);
} ;

JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_bean_Person_invokeJavaMethod
        (JNIEnv *env, jobject obj) {
    jclass clzz = env->GetObjectClass(obj);
    jmethodID setNameMethodId = env->GetMethodID(clzz, "setName", "(Ljava/lang/String;)V");
    jmethodID setAgeMethodId = env->GetMethodID(clzz, "setAge", "(I)V");

    //注意：不能直接输入字符串，需要的是 jstring 类型
    env->CallVoidMethod(obj, setNameMethodId, env->NewStringUTF("huangjian"));
    env->CallVoidMethod(obj, setAgeMethodId, 27);
}
#ifdef __cplusplus
}
#endif