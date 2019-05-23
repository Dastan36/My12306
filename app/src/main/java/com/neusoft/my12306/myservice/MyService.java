package com.neusoft.my12306.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    boolean isStart=false;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("MyService.onBind");
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyService.onCreate");
    }
    public void startPlayer(){

        System.out.println("播放音乐");
    }

    public void stopPlayer(){
        System.out.println("停止音乐");
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MyService.onStartCommand");
        isStart=true;

        new Thread(){
            int count=0;
            @Override
            public void run() {
                while(isStart){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("service程序再次监听。。"+(count++));
                }
            }
        }.start();

        //长连接
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isStart=false;
        System.out.println("MyService.onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("MyService.onUnbind");
        return super.onUnbind(intent);
    }
    class MyBinder extends Binder {


        public MyService getService(){
            return MyService.this;

        }
    }


}
