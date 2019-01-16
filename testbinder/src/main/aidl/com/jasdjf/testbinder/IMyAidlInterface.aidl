// IMyAidlInterface.aidl
package com.jasdjf.testbinder;

import com.jasdjf.testbinder.Book;//必须手动导入Book类

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    /*void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);*/
    void sayHello(int num);

    void addBook(in Book book);

    List<Book> getBookList();
}
