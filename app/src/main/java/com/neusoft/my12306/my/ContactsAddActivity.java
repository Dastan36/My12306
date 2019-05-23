package com.neusoft.my12306.my;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.neusoft.my12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsAddActivity extends AppCompatActivity {

    ListView lvMyContactAdd=null;

    List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    SimpleAdapter adapter =null;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_add);
       //找到id
        lvMyContactAdd= findViewById(R.id.lvMyContactAdd);

        btnSave= findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
//                data.putExtra("mydata","2019年4月30日10:40:39");

                HashMap map=new HashMap();
                map.put("pname","诸葛亮");
                map.put("pType","成人");
                map.put("pidType","身份证");
                map.put("pID","130183199805151567");
                map.put("pTel","18845121275");
                data.putExtra("newPeople",map);

                //将数据回传给 打开当前Activity的上一个Activity
                setResult(100,data);
                finish();
            }
        });
        //数据
        Map row1 = new HashMap();
        row1.put("title","姓名");
        row1.put("src",R.drawable.forward_25);


        Map row2 = new HashMap();
        row2.put("title","证件类型");
        row2.put("src",R.drawable.forward_25);



        Map row3 = new HashMap();
        row3.put("title","证件号码");
        row3.put("src",R.drawable.forward_25);


        Map row4 = new HashMap();
        row4.put("title","乘客类型");
        row4.put("src",R.drawable.forward_25);

        Map row5 = new HashMap();
        row5.put("title","电话");
        row5.put("src",R.drawable.forward_25);

        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);

        String [] from={"title","src"};
        int [] to={R.id.tvTitle,R.id.ivforword};
          //定义适配器
         adapter =new SimpleAdapter(this,data,R.layout.item_contacts_edit_layout,from,to);

         //绑定适配器
        lvMyContactAdd.setAdapter(adapter);
        lvMyContactAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){

                    //查询通讯录
                    queryPhoneContact();

                }

            }
        });

    }
    // 获取联系人
    private void queryPhoneContact(){


        //content://com.android.contacts     Content  Provider内容提供者
        ContentResolver cr = getContentResolver();//内容解析器

        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null, null, null);

        for (int i = 0; i <c.getColumnCount() ; i++) {
            System.out.println( "column:"+i+" ----"+c.getColumnName(i));
        }

        while (c.moveToNext()) {

            int _id = c.getInt(c.getColumnIndex(ContactsContract.Contacts._ID));
            //select * from table  where id=?,?
            String display_name = c.getString(c.getColumnIndex("display_name"));
            Cursor c2 =     cr.query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + "= ?",
                                        new String[] { _id +"" },
                                        null);  // 传入联系人_id
            String number = null;

            if (c2.moveToNext()) {
                number = c2.getString(c2
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }

            Log.d("My12306", _id + "," + display_name +",phone :"+number );

        }

        c.close();

    }

}
