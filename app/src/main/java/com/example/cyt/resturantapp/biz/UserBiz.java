package com.example.cyt.resturantapp.biz;

import com.example.cyt.resturantapp.bean.User;
import com.example.cyt.resturantapp.config.Config;
import com.example.cyt.resturantapp.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserBiz {

    public void login(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post()
                .url(Config.BASE_URL + "user_login")
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);
    }

    public void register(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post()
                .url(Config.BASE_URL + "user_register")
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);
    }
}
