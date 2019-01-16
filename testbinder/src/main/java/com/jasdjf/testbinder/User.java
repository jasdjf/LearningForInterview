package com.jasdjf.testbinder;

import java.io.Serializable;


/**
 * Serializable示例
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String userPhoneNum;

    public User(String userName,String userPhoneNum){
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                '}';
    }
}
