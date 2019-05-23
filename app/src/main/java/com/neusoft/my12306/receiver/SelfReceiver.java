package com.neusoft.my12306.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
   自定义的广播接收器
   +
 */
public class SelfReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("SelfReceiver", "接受到消息 ");
    }
}
