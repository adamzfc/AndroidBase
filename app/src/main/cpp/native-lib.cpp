//
// Created by adamzfc on 5/5/17.
//
#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_adamzfc_androidbase_test_jni_TestJniActivity_stringFromJNI(
        JNIEnv *env,
        jobject) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
