package com.neusoft.my12306.stations;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.neusoft.my12306.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListStationsActivity extends AppCompatActivity {

    private ListView lvAction;
//    SimpleAdapter adapter = null;
    ListAdapter adapter=null;
//    List<Map<String,Object>> data = new ArrayList();
        List<Station> data = new ArrayList();


    private  ListLetterView  listLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_action);



        //lvAction构造车站列表
        lvAction= findViewById(R.id.lvAction);
        //ListLetterView
        listLetter = findViewById(R.id.listLetter);

        //添加监听事件
        listLetter.setListener(new ListLetterView.LetterOnClickListener(){

            @Override
            public void onLetterClick(String letter) {
                Toast.makeText(ListStationsActivity.this, "letter:"+letter, Toast.LENGTH_SHORT).show();
                Integer po = firstLetter.get(letter);
                lvAction.setSelection(po);
            }
        });


        //通过程序 查询 select 查询出 车站列表
        data = StationUtils.queryAll(this);

        String[] from = {"letter","name"};
        int[] to = {R.id.tvStationLetter,R.id.tvStationName};
//        adapter = new SimpleAdapter(this,data,R.layout.item_list_stations_layout,from,to);
        adapter=new MyListAdapter(data);
        lvAction.setAdapter(adapter);


    }
    //想记录一下每个字母第一次出现的位置
    HashMap<String,Integer> firstLetter = new HashMap();

    //自己写的适配器
    class  MyListAdapter extends BaseAdapter {

        List<Station> myData = null;

        public MyListAdapter(List data) {
            this.myData = data;
            for(int i=0;i<myData.size();i++){

                Station myStation=myData.get(i);
                String letter=myStation.getSortOrder();

                if(!firstLetter.containsKey(letter)){
                    firstLetter.put(letter,i);//A---0  B-----5
                }
            }
        }


        @Override
        public int getCount() {

            return myData.size();
        }

        @Override
        public Object getItem(int position) {

            return myData.get(position);

        }

        @Override
        public long getItemId(int position) {

            return position;
        }
        /*
            什么缓存啥的
         */
        private class ViewHolder{

            private TextView letterView;
            private TextView stationNameView;


        }
        //渲染某一行的时候 执行
        /**
         *
         * @param position      需要渲染的 行索引
         * @param convertView   已经渲染过的缓存View对象
         * @param parent
         * @return
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            //返回单行需要显示的 控件
            if(convertView ==null){//初始没有渲染过，或者没有缓存
                //从xml 渲染一个View对象
                convertView = getLayoutInflater().inflate(R.layout.item_list_stations_layout, null);

                viewHolder = new ViewHolder();

                //findView 不用每次调用
                viewHolder.letterView = convertView.findViewById(R.id.tvStationLetter);
                viewHolder.stationNameView = convertView.findViewById(R.id.tvStationName);

                convertView.setTag(viewHolder);


            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }


            Station itemData = (Station) getItem(position);
            Integer firstLetterIndex = firstLetter.get(itemData.getSortOrder());


            /**
             *  view  -- LinearLayout
             *              tvStationLetter
             *              tvStationName
             */
            if (firstLetterIndex == position) {
                viewHolder.letterView.setVisibility(View.VISIBLE);//设置控件 可见
            }else{
                viewHolder.letterView.setVisibility(View.GONE);//设置控件 不可见
            }


            viewHolder.letterView.setText(itemData.getSortOrder());
            viewHolder.stationNameView.setText(itemData.getStationName());
            //给TextView 添加点击事件
            viewHolder.stationNameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView stationNameView = (TextView) v;
                    String stationName = stationNameView.getText().toString();

                    Intent intent = new Intent();
                    intent.putExtra("stationName",stationName);
                    setResult(10,intent);
                    finish();
                }
            });


            //返回当前行 需要展示的View视图
            return convertView;
        }
    }


}



