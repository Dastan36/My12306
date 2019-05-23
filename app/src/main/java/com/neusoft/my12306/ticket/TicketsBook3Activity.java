package com.neusoft.my12306.ticket;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketsBook3Activity extends AppCompatActivity {

     TextView tvAdd;
     MyAdapterList adapter = null;
     ArrayList<Map<String,Object>> data = new ArrayList<>();//默认没有数据， 当选择联系人后，通过adapter 更新ListView
    ListView lvBooks3List;
    TextView tvOrderAmount;
    private double price=  100.5;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book3);
        tvAdd=findViewById(R.id.tvAdd);

        lvBooks3List=findViewById(R.id.lvBooks3List);
        tvOrderAmount = findViewById(R.id.tvOrderAmount);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(TicketsBook3Activity.this,TicketsBook5Activity.class);
                startActivity(intent);
            }
        });




        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(TicketsBook3Activity.this,TicketsBook4Activity.class);
                startActivityForResult(intent,100);
            }
        });
//        String[] from = {"name","id","tele"};
//        int[] to = {
//                R.id.tvName,
//                R.id.tvID,
//                R.id.tvTel
//        };

        adapter = new MyAdapterList(data);

        //lvTicketsStep3
        lvBooks3List.setAdapter(adapter);

    }
    class   MyAdapterList  extends BaseAdapter{

        List list=null;

        public MyAdapterList(List list) {
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


            TextView tvName;
            TextView tvIdNo;
            TextView tvTel;
            ImageView ivResove;

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=new ViewHolder();

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.item_tickets_step3,null);
               viewHolder.tvName= convertView.findViewById(R.id.tvName);
               viewHolder.tvIdNo= convertView.findViewById(R.id.tvID);
               viewHolder.tvTel= convertView.findViewById(R.id.tvTel);
               viewHolder.ivResove= convertView.findViewById(R.id.ivResove);
               convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Map people= (Map) getItem(position);
            viewHolder.tvName.setText((String) people.get("name"));
            viewHolder.tvIdNo.setText((String) people.get("id"));
            viewHolder.tvTel.setText((String) people.get("tele"));
            viewHolder.ivResove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);

                    adapter.notifyDataSetChanged();
                    //计算订单总额
                    calCount(data.size()*price);

                }
            });
            return convertView;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentData) {

       if(intentData !=null) {

           List selectData = (List) intentData.getSerializableExtra("selectData");
//           Toast.makeText(this,selectData.toString(),Toast.LENGTH_SHORT).show();
           //通知 Adapter 调用notifyDataSetChanged（）；
//        selectData
           data.addAll(selectData);//设置跟adapter绑定的List
           adapter.notifyDataSetChanged();
           //计算订单总额
           calCount(data.size()*price);

       }


    }

    /**
     * 设置计算的钱
     */
    public void calCount(double money){

        tvOrderAmount.setText("订单总额："+money+"元");
    }


}
