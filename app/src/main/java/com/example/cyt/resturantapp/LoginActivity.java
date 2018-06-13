package com.example.cyt.resturantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;

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
                toOrderActivity();
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
