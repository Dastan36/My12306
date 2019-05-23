package com.neusoft.my12306.stations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StationUtils {


    private static String PATH ="/data/data/com.neusoft.my12306/databases/";
    private static String DB_NAME ="station.db";

    private static void init(Context context){

        File file = new File(PATH,DB_NAME);
        if(!file.exists()){//如果机器上不存在db
            try {
                //从Asserts目 读取db文件 写入到 /data/data/com.neusoft.my12306/databases/station.db
                InputStream in = context.getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int len = -1;
                while(      (len = in.read(bytes))!= -1  ){

                    fos.write(bytes,0,len);

                }

                fos.close();
                in.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static List<Station> queryAll(Context context){

        init(context);//



        List<Station> data = new ArrayList();
        String path = PATH+DB_NAME;
        //1 打开数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path,null);

        String sql = " select _id ,station_name,sort_order,tele_code,province,city from station order by sort_order ";
        Cursor cur = db.rawQuery(sql,null);//游标
//        cur-->meta
        Station station = null;
        while(cur.moveToNext()){
            //cur.moveToNext 返回true 指向有数据的行


            String stationName = cur.getString( cur.getColumnIndex("station_name"));
            String sortOrder = cur.getString( cur.getColumnIndex("sort_order"));
            if(sortOrder==null || sortOrder.equals("")){
                sortOrder="热门";
            }

            station = new Station();
            station.setStationName(stationName);
            station.setSortOrder(sortOrder);

            data.add(station);
            //
        }

        cur.close();
        db.close();

        return data;
    }


}
