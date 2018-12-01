//
// Created by bignox on 2018/11/30.
//
#include <jni.h>
/* Header for class com_adeveloperh_androidreversestudy_jni_Hello */

#include <android/log.h>

#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

//调用 Java 端方法
JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_book_Hello_test
        (JNIEnv *env, jobject obj) {
    jclass clazz = env->GetObjectClass(obj);
    jfieldID fieldId = env->GetFieldID(clazz, "property", "I");
    jmethodID methodId = env->GetMethodID(clazz, "function", "(ILjava/util/Date;[I)I");
    jint result = env->CallIntMethod(obj, methodId, 0L, NULL, NULL);
    LOGD("call function result is %d", result);
}

//调用 Java 成员变量并改变值，多态调用
JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_book_JNIDemo_sayHello
        (JNIEnv *env, jobject obj) {
    jclass clazz = env->GetObjectClass(obj);

    //调用 Java 的成员变量并改变其值
    jfieldID field_number = env->GetFieldID(clazz, "number", "I");
    jint number = env->GetIntField(obj, field_number);
    LOGD("JNIDemo number is %d", number);
    env->SetIntField(obj, field_number, 666);

    //调用 Java 多参数方法（方案一）
    jmethodID methodId_function = env->GetMethodID(clazz, "function", "(IDC)Z");
    //最后一个参数 java 中是 char 类型的，但是 java 中字符是 Unicode 双字节，C++ 中字符是单字节的，所有要在前边加 L ，变成宽字符
    jboolean result = env->CallBooleanMethod(obj, methodId_function, 10, 6.6, L'b');
    LOGD(" call method function result is %d", result);

    //调用 Java 多参数方法（方案二）使用 jvalue 类型
    jvalue *args = new jvalue[3];
    args[0].i = 10;
    args[1].d = 6.6;
    args[2].c = L'b';
    jboolean result2 = env->CallBooleanMethod(obj, methodId_function, args);
    LOGD(" call method function result is %d", result2);
    delete[]args;

    //多态时 C++ 中默认调用的是父类的方法，这里实现调用子类方法
    jfieldID fieldId_Father = env->GetFieldID(clazz, "father",
                                              "Lcom/adeveloperh/androidreversestudy/jni/book/Father;");
    //获取 father 字段的对象类型
    jobject object_father = env->GetObjectField(obj, fieldId_Father);
    //获取 father 对象的 class 对象
    jclass clazz_father = env->FindClass("com/adeveloperh/androidreversestudy/jni/book/Father");
    //获取 father 对象中的 function 方法的 id
    jmethodID methodId_father_function = env->GetMethodID(clazz_father, "function", "()V");
    //调用父类中 function 方法（会执行子类的方法）
    env->CallVoidMethod(object_father, methodId_father_function);
    //调用父类中 function 方法（执行的就是父类中的 function 方法）
    env->CallNonvirtualVoidMethod(object_father, clazz_father, methodId_father_function);
} ;


//创建 Java 对象
JNIEXPORT void JNICALL Java_com_adeveloperh_androidreversestudy_jni_book_JNIDemo_createJavaObject
        (JNIEnv *env, jobject obj) {
    jclass class_Date = env->FindClass("java/util/Date");
    //获取构造函数方法 id
    jmethodID mid_date_init = env->GetMethodID(class_Date, "<init>", "()V");

    //创建 Date 对象(方案一)
    jobject date = env->NewObject(class_Date, mid_date_init);
    jmethodID methodId_gettime = env->GetMethodID(class_Date, "getTime", "()J");
    jlong curTime = env->CallLongMethod(date, methodId_gettime);
    LOGD("current time is %ld", curTime);

    //创建 Date 对象,此时是非初始化的，需要调用 CallNonvirtualVoidMethod 进行初始化（方案二）(两个同时调用输出值会有问题)
//    jobject date2 = env->AllocObject(class_Date);
//    //调用构造方法
//    env->CallNonvirtualVoidMethod(date2, class_Date, mid_date_init);
//    jlong curTime2 = env->CallLongMethod(date2, methodId_gettime);
//    LOGD("current time is %ld",curTime2);
}

#ifdef __cplusplus
}
#endif


