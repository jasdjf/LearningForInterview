package com.jasdjf.testmvp;

public interface ILoginContract {
    interface View{
        String getUserName();
        String getPassword();
        void ShowLoginPB();
        void CancleLoginPB();
        void showLoginSucceed(UserBean user);
        void showLoginFailed();
        void clearUserName();
        void clearPassword();
        void setPresenter(Presenter presenter);
    }

    interface Presenter{
        void login();
        void clear();
    }
}
