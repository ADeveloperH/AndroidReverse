//
// Created by bignox on 2018/11/25.
//

#include <jni.h>
#include <android/log.h>

#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
extern "C"
JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_JNIUtils_printPersonMsg
        (JNIEnv *env, jclass clazz, jobject person) {
    jclass personClass = env->GetObjectClass(person);
    jmethodID getNameMethodId = env->GetMethodID(personClass, "getName", "()Ljava/lang/String;");
    jmethodID getAgeMethodId = env->GetMethodID(personClass, "getAge", "()I");
    jstring name = static_cast<jstring>(env->CallObjectMethod(person, getNameMethodId));
    jint age = env->CallIntMethod(person, getAgeMethodId);
    //注意这里 %s 不能直接输出 jstring ，需要转换为 char* 类型输出
    LOGD("This person name is %s and age is %d", env->GetStringUTFChars(name, 0), age);
}