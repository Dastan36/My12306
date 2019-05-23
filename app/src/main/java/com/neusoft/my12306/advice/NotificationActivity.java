package com.neusoft.my12306.advice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neusoft.my12306.MainActivity;
import com.neusoft.my12306.R;

public class NotificationActivity extends AppCompatActivity {

    private Button btnNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnNotify=findViewById(R.id.btnNotify);
        final NotificationManager nfmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // 通知的Builder
                Notification.Builder builder = new Notification.Builder(NotificationActivity.this);

                // 延迟Intent
                Intent intent = new Intent();
                intent.setClass(NotificationActivity.this, MainActivity.class);
                intent.putExtra("msg","2019年5月17日10:49:30");

                //  延迟Intent
                PendingIntent pIntent = PendingIntent.getActivity(NotificationActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //通过Builder 属性 创建 Notification

                Notification nf = builder
                        .setSmallIcon(R.drawable.add_user_25)
                        .setContentText("通知的内容.....")
                        .setSubText("通知副标题")
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pIntent)


                        .build();


                // 发布通知
                nfmgr.notify(1000, nf);

            }
        });



    }
}
