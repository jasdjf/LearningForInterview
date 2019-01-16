#include <jni.h>
#include <string>
#include <android/log.h>
#include <fcntl.h>

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "native", __VA_ARGS__))    // 定义LOGD类型
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native", __VA_ARGS__))    // 定义LOGI类型
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native", __VA_ARGS__))    // 定义LOGW类型
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "native", __VA_ARGS__))    // 定义LOGE类型

int fd = -1;

extern "C" {

/**
 * 成功返回0，失败返回-1
 */
jint Java_com_jasdjf_testjni_TestJni_openDevice(JNIEnv *env, jobject /* this */, jstring dev_path) {
    const char *i2c_path = env->GetStringUTFChars(dev_path, NULL);
    LOGD("dev_path: %s, len=%d\n", i2c_path, strlen(i2c_path));
    fd = open(i2c_path, O_RDWR);
    if (fd < 0) {
        LOGE("Error on opening the device file error code=%d\n", fd);
        return -1;
    }
    return 0;
}

/**
 * 成功返回0，失败返回-1
 */
jint Java_com_jasdjf_testjni_TestJni_closeDevice(JNIEnv *env, jobject /* this */) {
    return close(fd);
}

/**
 * 成功返回0，失败返回-1
 */
jint Java_com_jasdjf_testjni_TestJni_IICRead(JNIEnv *env, jobject /* this */, jbyteArray writebuf,
                                             jint writebytelen, jcharArray readbuf,
                                             jint readbytelen) {
    char pwritebuf[writebytelen + 1];
    unsigned char preadbuf[readbytelen];
    char a[2];

    jbyte *pelem = env->GetByteArrayElements(writebuf, 0);
    if (writebytelen > 0) {
        //pwritebuf[0] = 7;
        //pwritebuf[writebytelen + 1] = '\0';
        /*LOGD("writebytelen=%d\n", writebytelen);
        LOGD("pelem[0]=%d\n", pelem[0]);
        for (int i = 1; i < writebytelen; i++) {
            pwritebuf[i] = pelem[i];
        }
        LOGD("pwritebuf[1]=%d\n", pwritebuf[1]);*/
        LOGD("sizeof(pelem)=%d\n", (int)sizeof(pelem));
        LOGD("sizeof(writebytelen)=%d\n", (int)sizeof(writebytelen));
        LOGD("sizeof(0)=%d\n", (int)sizeof(a));
        write(fd, pelem, sizeof(a));
    }
    int ret = (int) read(fd, preadbuf, sizeof(preadbuf));
    LOGD("ret=%d\n", ret);
    if (ret >= 0) {
        LOGD("preadbuf[0]=%d\n", preadbuf[0]);
        jchar tmp[readbytelen];
        for (int i = 0; i < (readbytelen); i++) {
            tmp[i] = preadbuf[i];
        }
        env->SetCharArrayRegion(readbuf, 0, readbytelen, tmp);
    }
    env->ReleaseByteArrayElements(writebuf, pelem, 0);
    return ret;
}

/**
 * 成功返回0，失败返回-1
 */
jint Java_com_jasdjf_testjni_TestJni_IICWrite(JNIEnv *env, jobject /* this */, jcharArray writebuf,
                                              jint writelen) {
    jchar *writeelems = env->GetCharArrayElements(writebuf, NULL);
    unsigned char pwritebuf[writelen + 2];
    pwritebuf[0] = 6;
    pwritebuf[writelen + 1] = '\0';
    for (int i = 1; i < writelen; i++) {
        pwritebuf[i] = (unsigned char) (writeelems[i]);
    }
    int ret = (int)write(fd, pwritebuf, sizeof(pwritebuf));
    env->ReleaseCharArrayElements(writebuf, writeelems, 0);
    return ret;
}
}