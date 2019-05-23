package com.neusoft.my12306.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neusoft.my12306.R;

public class ServiceActivity extends AppCompatActivity {

    private Button btnStartActivity;

    private Button btnStopActivity;
    private Button btnBindActivity;
    private Button btnUnBindActivity;
    MyServiceConnection myServiceConnection = new MyServiceConnection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btnStartActivity= findViewById(R.id.btnStartActivity);

        btnStopActivity= findViewById(R.id.btnStopActivity);

        btnBindActivity=findViewById(R.id.btnBindActivity);
        btnUnBindActivity=findViewById(R.id.btnUnBindActivity);

        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ServiceActivity.this,MyService.class);
                startService(intent);
            }
        });

        btnStopActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ServiceActivity.this,MyService.class);
                stopService(intent);
            }
        });
        btnBindActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(ServiceActivity.this,MyService.class);

                bindService(intent,myServiceConnection,BIND_AUTO_CREATE);

            }
        });
        btnUnBindActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(ServiceActivity.this,MyService.class);

                unbindService(myServiceConnection);


            }
        });


    }
    MyService myService = null;

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println(name);
            System.out.println(service);

            MyService.MyBinder binder = (MyService.MyBinder) service;
            //可以通过service 控制serivce
            myService = binder.getService();
            myService.startPlayer();
            myService.stopPlayer();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println(name);
        }

    }


}

