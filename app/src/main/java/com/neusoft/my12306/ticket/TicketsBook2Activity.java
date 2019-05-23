package com.neusoft.my12306.ticket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.neusoft.my12306.R;
import com.neusoft.my12306.domain.Seat;

import java.util.ArrayList;
import java.util.List;
public class TicketsBook2Activity extends AppCompatActivity {

    ListView lvTicketsBook2;
    MyListAdapter adapter;
    List data;
    Button  btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book2);

        lvTicketsBook2= findViewById(R.id.lvTicketsBook2);
        btnOrder=findViewById(R.id.btnOrder);


        data = new ArrayList();
        data.add(new Seat("软座",10,300.0));
        data.add(new Seat("硬座",10,200.0));
        data.add(new Seat("无座",10,100.0));
        data.add(new Seat("高级软卧",10,400.0));


        adapter = new MyListAdapter(data);

        lvTicketsBook2.setAdapter(adapter);

    }
    //自己写的适配器
    class MyListAdapter extends BaseAdapter {

        List list = null;

        //通过构造器来赋值
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
            TextView seatName;
            TextView seatNum;
            TextView seatPrice;
            Button btnBook;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.item_tickets_step2, null);

                holder = new ViewHolder();
                holder.seatName = convertView.findViewById(R.id.tvseatName);
                holder.seatNum = convertView.findViewById(R.id.tvseatNum);
                holder.seatPrice = convertView.findViewById(R.id.tvseatPrice);
                holder.btnBook = convertView.findViewById(R.id.btnOrder);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();

            }
            final Seat seat = (Seat) getItem(position);

            holder.seatName.setText(seat.getSeatName());
            holder.seatNum.setText(seat.getSeatNum()+"张");
            holder.seatPrice.setText("￥"+seat.getSeatPrice());


            holder.btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TicketsBook2Activity.this,seat.getSeatName(),Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent();
                            intent.setClass(TicketsBook2Activity.this,TicketsBook3Activity.class);
                            startActivity(intent);
                }
            });

            return convertView;

        }


    }

}
