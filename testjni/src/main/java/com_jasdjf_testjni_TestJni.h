/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_jasdjf_testjni_TestJni */

#ifndef _Included_com_jasdjf_testjni_TestJni
#define _Included_com_jasdjf_testjni_TestJni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jasdjf_testjni_TestJni
 * Method:    openDevice
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jasdjf_testjni_TestJni_openDevice
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_jasdjf_testjni_TestJni
 * Method:    closeDevice
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_jasdjf_testjni_TestJni_closeDevice
  (JNIEnv *, jclass);

/*
 * Class:     com_jasdjf_testjni_TestJni
 * Method:    IICRead
 * Signature: ([BI[CI)I
 */
JNIEXPORT jint JNICALL Java_com_jasdjf_testjni_TestJni_IICRead
  (JNIEnv *, jclass, jbyteArray, jint, jcharArray, jint);

/*
 * Class:     com_jasdjf_testjni_TestJni
 * Method:    IICWrite
 * Signature: ([CI)I
 */
JNIEXPORT jint JNICALL Java_com_jasdjf_testjni_TestJni_IICWrite
  (JNIEnv *, jclass, jcharArray, jint);

#ifdef __cplusplus
}
#endif
#endif