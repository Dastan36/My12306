package com.neusoft.my12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsBook4Activity extends AppCompatActivity {
    ListView lvTicketsBook4;
    MyListAdapter adapter;
    ArrayList<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    ArrayList<Map<String,Object>> selectData = new ArrayList();
    Button btnAddPeople=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tickets_book4);

        lvTicketsBook4 = findViewById(R.id.lvTicketsBook4);

         btnAddPeople = findViewById(R.id.btnAddPeople);
         btnAddPeople.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 Toast.makeText(TicketsBook4Activity.this,selectData.toString(),Toast.LENGTH_SHORT).show();
                 intent.putExtra("selectData",selectData);
                 setResult(100,intent);
                 finish();
             }
         });

        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("name","李茜");
        map1.put("id","身份证：130183199805151567");
        map1.put("tele","电话号：18845121275");
        data.add(map1);

        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("name","朱桓民");
        map2.put("id","身份证：130183199805151567");
        map2.put("tele","电话号：18845121275");
        data.add(map2);

        Map<String,Object> map3=new HashMap<String,Object>();
        map3.put("name","张丹");
        map3.put("id","身份证：130183199805151567");
        map3.put("tele","电话号：18845121275");
        data.add(map3);

         adapter=new MyListAdapter(data);
         lvTicketsBook4.setAdapter(adapter);
    }
    class MyListAdapter extends BaseAdapter {

        List list = null;

        public MyListAdapter(List list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            CheckBox ckSelectPerson;
            TextView tvName;
            TextView tvIdNo;
            TextView tvTel;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_tickets_step4, null);
                viewHolder = new ViewHolder();
                viewHolder.ckSelectPerson = convertView.findViewById(R.id.ckSelectPeo);
                viewHolder.tvName = convertView.findViewById(R.id.tvName);
                viewHolder.tvIdNo = convertView.findViewById(R.id.tvID);
                viewHolder.tvTel = convertView.findViewById(R.id.tvTel);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            //给checkBox添加事件
            viewHolder.ckSelectPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox ck = (CheckBox) v;
                    Map selectPerson = (Map) getItem(position);
                    if (ck.isChecked()) {
                        if (!selectData.contains(selectPerson)) {
                            selectData.add(selectPerson);
                        }
                    } else {
                        if (selectData.contains(selectPerson)) {
                            selectData.remove(selectPerson);
                        }
                    }

                    Toast.makeText(TicketsBook4Activity.this, selectData.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            Map person = (Map) getItem(position);
            viewHolder.tvName.setText((String) person.get("name"));
            viewHolder.tvIdNo.setText((String) person.get("id"));
            viewHolder.tvTel.setText((String) person.get("tele"));

            return convertView;
        }
    }


    //Activity 在上边添加一个小人

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_tickets_step4,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Activity 监听菜单创建的事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.menu_contact_add){

//
//            Intent intent = new Intent();
//            intent.putExtra("selectData",selectData);
//
//            setResult(100,intent);
//            finish();
        }



        return true;
    }




}
