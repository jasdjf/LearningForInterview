package com.jasdjf.testso;

/**
 * Created by zhuangwei on 2018/3/12.
 */

public class SayHello {
    static{
        System.loadLibrary("hello");
    }
    //这里想要调用成功则必须保证so库中的方法名必须为Java+包名(.号以下划线代替)+类名+类中的native方法名
    //例如：此类中的stringFromJNI方法在C code中就必须定义为Java_com_jasdjf_testso_SayHello_stringFromJNI()
    //int Java_com_jasdjf_testso_SayHello_stringFromJNI(){
    //  return 123;
    //}
    public static native int stringFromJNI();
}
