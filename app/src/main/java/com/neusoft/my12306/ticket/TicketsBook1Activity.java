package com.neusoft.my12306.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsBook1Activity extends AppCompatActivity {

    ListView lvTicketsBook;

    SimpleAdapter adapter=null;
    List<Map<String,Object>> data = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book1);


        lvTicketsBook= findViewById(R.id.lvTicketsBook);
        lvTicketsBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(TicketsBook1Activity.this,TicketsBook2Activity.class);
                startActivity(intent);
            }
        });
        data=initData();
        String [] from={
                "trainName",
                "ivShi",
                "ivZhong",
                "fromTime",
                "toTime",
                "seat1",
                "seat2",
                "seat3",
                "seat4",

        };
        int [] to={
                R.id.tvTrainName,
                R.id.ivShi,
                R.id.ivZhong,
                R.id.tvFromTime,
                R.id.tvToTime,
                R.id.tvSeat1,
                R.id.tvSeat2,
                R.id.tvSeat3,
                R.id.tvSeat4,
        };
        adapter = new SimpleAdapter(this,data,R.layout.item_tickets_step1 ,from,to);

        lvTicketsBook.setAdapter(adapter);
    }

    private List<Map<String, Object>> initData() {

        List<Map<String,Object>> list=new ArrayList();
        Map<String,Object> map=new HashMap();
        map.put("trainName","Z138");
        map.put("ivShi",R.drawable.flg_shi);
        map.put("ivZhong",R.drawable.flg_zhong);
        map.put("fromTime","14:00");
        map.put("toTime","8:00(一日)");
        map.put("seat1","高级:200");
        map.put("seat2","硬卧:200");
        map.put("seat3","无座:200");
        map.put("seat4","硬座:200");
        list.add(map);



        Map<String,Object> map1=new HashMap();
        map1.put("trainName","D205");
        map1.put("ivShi",R.drawable.flg_shi);
        map1.put("ivZhong",R.drawable.flg_zhong);
        map1.put("fromTime","14:00");
        map1.put("toTime","8:00(一日)");
        map1.put("seat1","高级:200");
        map1.put("seat2","硬卧:200");
        map1.put("seat3","无座:200");
        map1.put("seat4","硬座:200");
        list.add(map1);


        return list;

    }
}
