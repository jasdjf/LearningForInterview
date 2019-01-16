package com.jasdjf.testmvp;

public interface OnLoginListener {
    void onLoginSucceed(UserBean user);
    void onLoginFailed();
}
