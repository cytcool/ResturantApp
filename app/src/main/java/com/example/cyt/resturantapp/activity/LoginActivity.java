package com.example.cyt.resturantapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyt.resturantapp.R;
import com.example.cyt.resturantapp.UserInfoHolder;
import com.example.cyt.resturantapp.bean.User;
import com.example.cyt.resturantapp.biz.UserBiz;
import com.example.cyt.resturantapp.net.CommonCallback;
import com.example.cyt.resturantapp.utils.T;

public class LoginActivity extends BaseActivity {
    
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;

    private UserBiz mUserBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();

        initEvent();
    }

    private void initEvent() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    T.showToast("账号或者密码不能为空");
                    return;
                }

                startLoadingProgress();

              mUserBiz.login(username, password, new CommonCallback<User>() {
                  @Override
                  public void onError(Exception e) {
                      stopLoadingProgress();
                      T.showToast(e.getMessage());
                  }

                  @Override
                  public void onSuccess(User response) {
                      stopLoadingProgress();
                      T.showToast("登录成功");
                      //保存用户信息
                      UserInfoHolder.getInstance().setUser(response);
                      toOrderActivity();
                  }

              });
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });
    }

    private void toOrderActivity(){
        Intent intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
        finish();
    }

    private void toRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mEtUsername = findViewById(R.id.id_ed_username);
        mEtPassword = findViewById(R.id.id_ed_password);
        mBtnLogin = findViewById(R.id.id_btn_login);
        mTvRegister = findViewById(R.id.id_tv_register);
    }
}
