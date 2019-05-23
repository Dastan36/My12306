package com.neusoft.my12306.order;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order2Activity extends AppCompatActivity {
    List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;
    private ArrayAdapter adapter1;
    private ListView lvOrder2;
    private Button btnChanKan;
    ListView lvDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        Map row1 = new HashMap();
        row1.put("passengerName","李茜");
        row1.put("seatNum","4车51号");
        data.add(row1);


        Map row2 = new HashMap();
        row2.put("passengerName","朱桓民");
        row2.put("seatNum","4车52号");
        data.add(row2);

        String[] from = {
                "passengerName",
                "seatNum"

        };
        int to[]={
                R.id.PeoName,
                R.id.seatNum

        };

        adapter=new SimpleAdapter(this,data,R.layout.item_order_step3,from,to);

        lvOrder2= findViewById(R.id.lvOrder2);
        lvOrder2.setAdapter(adapter);
        btnChanKan=findViewById( R.id.btnChanKan);
        btnChanKan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;

                dialog = new AlertDialog.Builder(Order2Activity.this)
                        .setTitle("我的二维码")
                        //.setMessage("确定退出吗？")
                        .setPositiveButton("确定", null)
                        .create();
                       dialog .show();
            }

        });
        lvOrder2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog dialog;
            dialog = new AlertDialog.Builder(Order2Activity.this)
                    .setIcon(R.drawable.administrator_25)
                    .setTitle("请选择操作")
                    //.setMessage("确定退出吗？")
                    .setSingleChoiceItems(new String[]{"退票", "改签"}, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }


    });

}
}

