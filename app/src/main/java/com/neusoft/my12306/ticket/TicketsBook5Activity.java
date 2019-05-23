package com.neusoft.my12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.MainActivity;
import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsBook5Activity extends AppCompatActivity {

     ListView lvOrderList;
     SimpleAdapter adapter;
     List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
      Button btnUnpay;
      Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book5);


        Map row1 = new HashMap();
        row1.put("passengerName","郭靖");
        row1.put("seatNum","3车51号");
        data.add(row1);


        Map row2 = new HashMap();
        row2.put("passengerName","黄蓉");
        row2.put("seatNum","4车51号");
        data.add(row2);

        String[] from = {
                "passengerName",
                "seatNum"

        };
        int to[]={
                R.id.PeoName,
                R.id.seatNum

        };

        adapter=new SimpleAdapter(this,data,R.layout.item_tickets_step5,from,to);

        lvOrderList= findViewById(R.id.lvOrderList);
        lvOrderList.setAdapter(adapter);


        btnUnpay=findViewById( R.id.btnUnpay);
        btnPay=findViewById( R.id.btnPay);
        btnUnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(TicketsBook5Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(TicketsBook5Activity.this,TicketsBook6Activity.class);
                startActivity(intent);
            }
        });
    }
}
