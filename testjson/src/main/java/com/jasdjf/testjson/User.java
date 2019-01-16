package com.jasdjf.testjson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuangwei on 2018/2/28.
 */

public class User {
    private String name;

    private int age;

    //@SerializedName("is_man")
    //不加SerializedName的时候，json字符串中的字符串必须与字段名isMan一样，否则解析失败，赋为默认值
    //同时还可以用以下方式添加多个字符串支持
    @SerializedName(value = "is_man",alternate = {"is_Man","isMan"})
    private boolean isMan;

    private String sex;

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:"+name+",age:"+age+",isMan:"+isMan+",sex:"+sex;
    }
}
