package com.example.cyt.resturantapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cyt.resturantapp.R;
import com.example.cyt.resturantapp.UserInfoHolder;
import com.example.cyt.resturantapp.bean.User;
import com.example.cyt.resturantapp.biz.UserBiz;
import com.example.cyt.resturantapp.net.CommonCallback;
import com.example.cyt.resturantapp.utils.T;


public class RegisterActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;
    private Toolbar toolbar;

    private UserBiz mUserBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //setUpToolbar();
        initView();
        initEvent();

        setTitle("注册");

        mUserBiz = new UserBiz();
    }


    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();

                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    T.showToast("账号或者密码不能为空");
                    return;
                }

                if (!password.equals(repassword)){
                    T.showToast("两次输入的密码不一致");
                }

                startLoadingProgress();

                mUserBiz.register(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("注册成功,用户名为："+response.getUsername());

                        LoginActivity.launch(RegisterActivity.this,response.getUsername(),response.getPassword());
                        finish();
                    }

                });
            }
        });
    }

    private void initView() {
        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);
        mEtRePassword = findViewById(R.id.id_et_rePassword);
        mBtnRegister = findViewById(R.id.id_btn_register);
    }

    protected void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
