package com.neusoft.my12306;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.my12306.ticket.TicketsBook1Activity;
import com.neusoft.my12306.stations.ListStationsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketsFragment extends Fragment {


    TextView tvStationFrom;
    TextView tvStationTo;
    ImageView opposite_arrows;
    TextView  tvdataTime;
    Button btselect;
    final  int version=1;//定义版本号
    private TextView tvHistory1;
    private TextView tvHistory2;


    public TicketsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }


    //执行 Activity onCreate

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //当Activity创建时执行


        //获取Activity
        tvStationFrom = getActivity().findViewById(R.id.tvFromCity);
        tvStationTo = getActivity().findViewById(R.id.tvToCity);

        opposite_arrows = getActivity().findViewById(R.id.opposite_arrows);

        tvdataTime=getActivity().findViewById(R.id.tvdataTime);

        btselect= getActivity().findViewById(R.id.btselect);
        tvHistory1=  getActivity().findViewById(R.id.tvHistory1);
        tvHistory2=  getActivity().findViewById(R.id.tvHistory2);



        btselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取出发城市和到达城市，放到数据库中存储
                String from=tvStationFrom.getText().toString();
                String to=tvStationTo.getText().toString();

                String dbName="history.db";//建数据库
                //建表
                StationHistorySQLiteOpenHelper helper = new StationHistorySQLiteOpenHelper(getActivity(), dbName, null, version);

                SQLiteDatabase db = helper.getWritableDatabase();

                //写入到数据库中去
                ContentValues contentValues = new ContentValues();

                contentValues.put("station_name",from+"--"+to);
                contentValues.put("create_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                db.insert("history",null ,contentValues);

                if(db != null){
                    db.close();
                }

                //判断 1.0 版本号， 2.0 修改的语句


                Intent intent=new Intent();
                intent.setClass(getActivity(), TicketsBook1Activity.class);
                startActivity(intent);
//                getActivity().finish();
            }
        });

        tvdataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String old=tvdataTime.getText().toString().split(" ")[0];//得到默认的时间
                String oldYear=old.split("-")[0];
                String oldMonth=old.split("-")[1];
                String oldDay=old.split("-")[2];
                DatePickerDialog picker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar d=Calendar.getInstance();
                        d.clear();
                        d.set(year,month,dayOfMonth);
                        String weekDay= DateUtils.formatDateTime(getActivity(),d.getTimeInMillis(),DateUtils.FORMAT_SHOW_WEEKDAY);
                        tvdataTime.setText(year+"-"+(month+1)+"-"+dayOfMonth+" "+weekDay);

                    }
                },Integer.parseInt(oldYear),Integer.parseInt(oldMonth)-1,Integer.parseInt(oldDay));

                picker.show();
            }
        });


        //出发城市

        tvStationFrom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"选择[出发城市]",Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setClass(getActivity(), ListStationsActivity.class);
                startActivityForResult(intent,1);


            }
        });



        //到达城市
        tvStationTo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"选择[到达城市]",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ListStationsActivity.class);
                startActivityForResult(intent,2);
            }
        });


        opposite_arrows.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //添加动画


                final String from = tvStationFrom.getText().toString();
                final String to = tvStationTo.getText().toString();
//
//                tvStationTo.setText(from);


                TranslateAnimation anileft = new TranslateAnimation(0, 150, 0,0);
                anileft.setInterpolator(new LinearInterpolator());//运行速率
                anileft.setDuration(500);
                anileft.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvStationFrom.clearAnimation();
                        tvStationFrom.setText(to);
                    }
                });
                tvStationFrom.startAnimation(anileft);


                TranslateAnimation aniRight = new TranslateAnimation(0, -150, 0,0);
                aniRight.setInterpolator(new LinearInterpolator());//运行速率
                aniRight.setDuration(500);
                aniRight.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvStationTo.clearAnimation();
                        tvStationTo.setText(from);
                    }
                });
                //让右侧的 到达城市的TextView控件执行 动画
                tvStationTo.startAnimation(aniRight);

            }
        });

    }
    /**
     * 每次加载页面 重新查询数据库
     */
    @Override
    public void onResume() {
        super.onResume();
        //查询数据库

        String dbName="history.db";

        StationHistorySQLiteOpenHelper helper=new StationHistorySQLiteOpenHelper(getActivity(),dbName,null,version);

        SQLiteDatabase db = helper.getReadableDatabase();
        //        db.rawQuery()
        Cursor cr = db.query("history", new String[]{"id", "station_name", "create_time"},
                null, null,
                null, null, "create_time  desc");
        String stationName1=null;
        String stationName2=null;
        if(cr.moveToNext()){
            stationName1=cr.getString(cr.getColumnIndex("station_name"));
            tvHistory1.setText(stationName1);
            if(cr.moveToNext()){
                stationName2=cr.getString(cr.getColumnIndex("station_name"));
                tvHistory2.setText(stationName2);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String stationName = data.getStringExtra("stationName");
        Toast.makeText(getActivity(),"stationName:"+stationName,Toast.LENGTH_SHORT).show();
        if(requestCode == 1){
            //放到【出发】城市的TextView中
            tvStationFrom.setText(stationName);

        }else{
            //放到【到达】城市的TextView中
            tvStationTo.setText(stationName);

        }


    }

    class  StationHistorySQLiteOpenHelper  extends SQLiteOpenHelper{

        public StationHistorySQLiteOpenHelper(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //建表语句
            StringBuffer createSql = new StringBuffer();

            createSql.append( "  CREATE TABLE history (								");
            createSql.append( "  		id	INTEGER NOT NULL DEFAULT increatement,"  );
            createSql.append( "  		station_name	TEXT NOT NULL,            "  );
            createSql.append( "  		create_time	TEXT NOT NULL,                "  );
            createSql.append( "  		PRIMARY KEY(id)                           "  );
            createSql.append( "  		                                          "  );
            createSql.append( "  )	                                              "  );

            db.execSQL(createSql.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String info = String.format("升级了，olderversion：%s,newVersion:%s",oldVersion,newVersion );

            System.out.println(info);



//            if(oldVersion == 2){
//
//                //进行数据库的补丁 补丁的版本2
//            }else{
//
//            }

        }
    }

}

