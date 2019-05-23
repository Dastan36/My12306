package com.neusoft.my12306;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.my12306.order.Order1Activity;
import com.neusoft.my12306.order.Order2Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private ListView lvOrderStatus;
    MyOrderListAdapter adapter;
    List data1=new ArrayList();//待支付订单数据
    List data2=new ArrayList();//全部订单数据
    ArrayList data=new ArrayList();
    private TextView tvDaizhifu;
    private TextView tvyizhifu;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvOrderStatus=getActivity().findViewById(R.id.lvOrderStatus);

        initData();

        data.clear();
        data.addAll(data1);


        tvDaizhifu = getActivity().findViewById(R.id.tvDaizhifu);
        tvyizhifu = getActivity().findViewById(R.id.tvyizhifu);

        tvDaizhifu.setOnClickListener(new OrderStatusClickListener());
        tvyizhifu.setOnClickListener(new OrderStatusClickListener());

        adapter=new MyOrderListAdapter(data);
        lvOrderStatus.setAdapter(adapter);
        //为未支付listView添加点击事件
        lvOrderStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyOrderListAdapter adapter=new MyOrderListAdapter(data);
                String orderStatus= (String) adapter.getItem(position);
                if(("未支付".equals(orderStatus))){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), Order1Activity.class);

                    intent.putExtra("position", position);

                    startActivityForResult(intent,1);
//                    Intent intent=new Intent();
//                    intent.setClass(getActivity(), Order1Activity.class);
//                    startActivity(intent);

                }else if(("已支付".equals(orderStatus))){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), Order2Activity.class);

                    startActivity(intent);
                }

            }
        });

    }
    class OrderStatusClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //清空原有listView中的数据
            data.clear();

            //设置新的 数据
            if(v.getId() == R.id.tvyizhifu){
                tvyizhifu.setBackgroundColor(getResources().getColor(R.color.blue));
                tvyizhifu.setTextColor(getResources().getColor(R.color.white));
                tvDaizhifu.setBackgroundColor(getResources().getColor(R.color.light_blue));
                tvDaizhifu.setTextColor(getResources().getColor(R.color.black));
                data.addAll(data2);
            }else{
                tvDaizhifu.setBackgroundColor(getResources().getColor(R.color.blue));
                tvDaizhifu.setTextColor(getResources().getColor(R.color.white));
                tvyizhifu.setBackgroundColor(getResources().getColor(R.color.light_blue));
                tvyizhifu.setTextColor(getResources().getColor(R.color.black));
                data.addAll(data1);
            }

            //通过适配器重新渲染ListView
            adapter.notifyDataSetChanged();
        }
    }
    public void initData(){

        data1.add("未支付");
        data1.add("未支付");




        data2.add("已支付");
        data2.add("未支付");
        data2.add("已取消");



    }
    //自定义适配器
    class MyOrderListAdapter  extends BaseAdapter{
        List list;

        public MyOrderListAdapter(List list) {
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
        class Holder{
            TextView tvOrderStatus;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
           if(convertView==null){
               convertView = getLayoutInflater().inflate(R.layout.item_order_step1, null);
               holder=new Holder();
               holder.tvOrderStatus= convertView.findViewById(R.id.tvOrderStatus);
               convertView.setTag(holder);
           }else {
              holder= (Holder) convertView.getTag();
           }
/*

 */
            String orderStatus = (String) getItem(position);

            holder.tvOrderStatus.setText(orderStatus);

            if("未支付".equals(orderStatus)){
                holder.tvOrderStatus.setTextColor(getResources().getColor(R.color.orange));
            }else if("已支付".equals(orderStatus)){
                holder.tvOrderStatus.setTextColor(getResources().getColor(R.color.blue));
            }else{
                holder.tvOrderStatus.setTextColor(getResources().getColor(R.color.grey));
            }



            return convertView;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);
        int position=intentData.getIntExtra("position",0);
        if(requestCode==1){
            data.set(position,"已取消");

        }else {
            data.set(position,"已支付");
        }
        adapter.notifyDataSetChanged();
    }
}
