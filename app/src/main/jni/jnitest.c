#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_hongcheng_learndemo_nativeInterface_JNITest_test(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "hehe");
}