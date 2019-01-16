package com.jasdjf.testmvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements ILoginContract.View {

    private EditText mEtUser;
    private EditText mEtPwd;
    private ProgressBar mProgressBar;
    //private LoginPresenter presenter = new LoginPresenter(this);
    private ILoginContract.Presenter mPresenter;
    private Button mBtnLogin;
    private Button mBtnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoginPresenter(this);
        initView();
    }

    private void initView() {
        mEtUser = (EditText) findViewById(R.id.et_user);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnClear = (Button) findViewById(R.id.btn_clear);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName() {
        return mEtUser.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPwd.getText().toString();
    }

    @Override
    public void ShowLoginPB() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void CancleLoginPB() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoginSucceed(UserBean user) {
        Toast.makeText(this, user.getUserName() + " login succeed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearUserName() {
        mEtUser.setText("");
    }

    @Override
    public void clearPassword() {
        mEtPwd.setText("");
    }

    @Override
    public void setPresenter(@NonNull ILoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
