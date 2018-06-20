package com.example.cyt.resturantapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cyt.resturantapp.R;
import com.example.cyt.resturantapp.UserInfoHolder;
import com.example.cyt.resturantapp.bean.User;
import com.example.cyt.resturantapp.biz.UserBiz;
import com.example.cyt.resturantapp.net.CommonCallback;
import com.example.cyt.resturantapp.utils.T;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

public class LoginActivity extends BaseActivity {
    
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;

    private UserBiz mUserBiz;

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();

        initEvent();

        initIntent(getIntent());

        mUserBiz = new UserBiz();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        initIntent(intent);
    }

    private void initIntent(Intent intent) {
        if (intent == null){
            return;
        }

        String username = intent.getStringExtra(KEY_USERNAME);
        String password = intent.getStringExtra(KEY_PASSWORD);

        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            return;
        }

        mEtUsername.setText(username);
        mEtPassword.setText(password);
    }

    private void initView() {
        mEtUsername = findViewById(R.id.id_ed_username);
        mEtPassword = findViewById(R.id.id_ed_password);
        mBtnLogin = findViewById(R.id.id_btn_login);
        mTvRegister = findViewById(R.id.id_tv_register);
    }

    public static void launch(Context context, String username, String password) {
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KEY_USERNAME,username);
        intent.putExtra(KEY_PASSWORD,password);
        context.startActivity(intent);
    }
}
