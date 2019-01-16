package com.jasdjf.testmvp;

import android.os.Handler;

public class LoginPresenter implements ILoginContract.Presenter {
    //private ILoginView iLoginView;
    private ILoginContract.View mView;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginContract.View mView){
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void clear(){
        mView.clearUserName();
        mView.clearPassword();
    }

    @Override
    public void login() {
        mView.ShowLoginPB();
        login(mView.getUserName(), mView.getPassword(), new OnLoginListener() {
            @Override
            public void onLoginSucceed(final UserBean user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.CancleLoginPB();
                        mView.showLoginSucceed(user);
                    }
                });
            }

            @Override
            public void onLoginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.CancleLoginPB();
                        mView.showLoginFailed();
                    }
                });
            }
        });
    }

    private void login(final String userName, final String passwd, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(loginJudge(userName,passwd)){
                    UserBean user = new UserBean();
                    user.setUserName(userName);
                    user.setPasswd(passwd);
                    listener.onLoginSucceed(user);
                } else {
                    listener.onLoginFailed();
                }
            }
        }).start();
    }

    private boolean loginJudge(String userName,String passwd){
        return userName.equals("jasdjf") && passwd.equals("123456");
    }
}
