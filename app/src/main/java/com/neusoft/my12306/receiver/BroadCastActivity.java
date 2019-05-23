package com.neusoft.my12306.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.neusoft.my12306.R;

public class BroadCastActivity extends AppCompatActivity {

    private Button btnSendBroadCast;
    private Button btnregisterBroadCast;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        btnSendBroadCast=findViewById(R.id.btnSendBroadCast);
        btnSendBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(BroadCastActivity.this,"11111",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction("com.neusoft.action.DemoAction");
                sendBroadcast(intent);

            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SelfReceiver", "动态注册的 广播接收器");

            }
        };

        //动态的广播接收器
        btnregisterBroadCast=findViewById(R.id.btnregisterBroadCast);
        btnregisterBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.neusoft.action.DemoAction");
                registerReceiver(receiver,intentFilter);
            }
        });




    }
}
