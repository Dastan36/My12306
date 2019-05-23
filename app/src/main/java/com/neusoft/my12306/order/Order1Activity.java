package com.neusoft.my12306.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.neusoft.my12306.MainActivity;
import com.neusoft.my12306.R;
import com.neusoft.my12306.ticket.TicketsBook5Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order1Activity extends AppCompatActivity {
    List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;
    private ListView lvOrder1;
    Button btnUnpay;
    Button btnPay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order1);

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

        adapter=new SimpleAdapter(this,data,R.layout.item_order_step2,from,to);

        lvOrder1= findViewById(R.id.lvOrder1);
        lvOrder1.setAdapter(adapter);
        btnUnpay=findViewById( R.id.btnUnpay);
        btnPay=findViewById( R.id.btnPay);
        btnUnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();//取出刚才传递过来的数据
                 int position = intent.getIntExtra("position",0);


                 intent.putExtra("position",position);

//                Toast.makeText(Order1Activity.this,"传过来",Toast.LENGTH_SHORT).show();

                setResult(1,intent);
                finish();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                int position = intent.getIntExtra("position",0);

                intent.putExtra("position",position);
               setResult(2,intent);
               finish();
            }
        });
    }
}
