package com.neusoft.my12306;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //获取用户名密码
        //如果已经登陆过，就只加跳到MyActivity

        String userName="";
        String password="";
        SharedPreferences shared=getSharedPreferences("login_user", Context.MODE_PRIVATE);
        userName=shared.getString("userName","");
        password=shared.getString("password","");
        if("admin".equals(userName)&&"123456".equals(password)){
            Intent intent=new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);

        }else{
            Intent intent=new Intent();
            intent.setClass(this,LoginActivity.class);
            startActivity(intent);

        }

        Log.v("SplashActivity", "SplashActivity.onCreate");//黑色，输出冗余消息
        Log.d("SplashActivity", "SplashActivity.onCreate");//蓝色，输出调试消息
        Log.i("SplashActivity", "SplashActivity.onCreate");//绿色，输出普通消息
        Log.w("SplashActivity", "SplashActivity.onCreate");//橙色，输出警告消息
        Log.e("SplashActivity", "SplashActivity.onCreate");//红色，输出错误消息
        finish();


    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("SplashActivity.onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("SplashActivity.onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("SplashActivity.onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("SplashActivity.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("SplashActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("SplashActivity.onDestroy");
    }


}
