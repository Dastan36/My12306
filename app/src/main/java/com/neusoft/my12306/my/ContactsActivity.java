package com.neusoft.my12306.my;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.neusoft.my12306.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsActivity extends AppCompatActivity {

    ListView lvContacts =null;
//    ListAdapter adapter=null;
    SimpleAdapter adapter=null;
    List<Map<String,Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //ActionBar  添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //zhaoListView
//        lvContacts= findViewById(R.id.lvContacts);
//        String data[]=new String [28];
//        for (int i = 0; i <data.length ; i++) {
//          data[i]="数目"+i;
//
//        }
//
//        adapter=new ArrayAdapter(this,R.layout.item_contacts_layout,data);//不对
//        //通过适配器将=绑定数据和ListView
//        lvContacts.setAdapter(adapter);
        //根据ID找ListView
        lvContacts= findViewById(R.id.lvContacts);
        //数据
        list=new ArrayList();
//        for (int i = 0; i <15; i++) {
//
//            Map<String,Object> people=new HashMap();
//            people.put("pname","李茜"+i+":学生");
//            people.put("pID","身份证:130183199805151567");
//            people.put("pTel","电话:18845121275");
//            list.add(people);
//        }
        //通过适配器将绑定数据和ListView
        adapter=new SimpleAdapter(this,list,R.layout.item_contacts_layout,
                new String []{"name","idNo","telphone"},
                new int []{R.id.tvName,R.id.tvID,R.id.tvTel});
        lvContacts.setAdapter(adapter);


        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //在内部类中访问list变量
               HashMap person= (HashMap) list.get(position);
                Intent intent =new Intent();
                intent.putExtra("person",person);
                intent.setClass(ContactsActivity.this,ContactEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new QueryContactsAsyncTask().execute();
    }
    //异步任务
    class QueryContactsAsyncTask extends AsyncTask<String,String,List> {

        @Override
        protected List doInBackground(String... strings) {

            ArrayList<Map> list = new ArrayList();

            HttpClient client = new DefaultHttpClient();
            String uri = "http://192.168.81.11:8080/ssm/contact/list";
            HttpPost post =  new HttpPost(uri);
            try {
                HttpResponse response = client.execute(post);

                //使用工具方法 读取 response的流
                String json = EntityUtils.toString( response.getEntity());
                Log.i("QueryContactsAsyncTask","json:"+json);

                Gson gson = new GsonBuilder().create();
                list = gson.fromJson(json, new TypeToken<List<HashMap>>() {}.getType());
//                list = gson.fromJson(json, ArrayList.class);
//
                System.out.println("list.size():"+list.size());
//                for (Contact contact : list) {
//                    System.out.println(contact);
//                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List list1) {
            super.onPostExecute(list1);
            list.clear();


            list.addAll(list1);
            //
            adapter.notifyDataSetChanged();
        }
    }
    /**
     * 新的Activity 关闭时回传数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        Log.i("ContactsActivity","在新的Activity关闭的时候 调用");

//        String mydate=intent.getStringExtra("newPeople");
        //将上一个Activity数据传回到这个Activity
        HashMap newPeople=(HashMap) intent.getSerializableExtra("newPeople");
        //添加到联系人列表中
        list.add(newPeople);
        //通知adapter重新绑定listView和传来的新的数据和原来的数据
        adapter.notifyDataSetChanged();
    }

    /*
   监听菜单事件
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("ContactsActivity","菜单被点击....");
        //如果点击的 是 返回菜单
        //R.id
        if(item.getItemId() == android.R.id.home){
            //finish当前 Activity
            finish();
        }else  if(item.getItemId() == R.id.menu_contact_add){
         //添加按钮
            Intent intent=new Intent();
            intent.setClass(this,ContactsAddActivity.class);
           // startActivity(intent);
            //z在这个Activity上启动一个新的Activity，新的Activity关闭时带回来数据
            startActivityForResult(intent,1);

        }
//        return super.onOptionsItemSelected(item);  true代表不给父级，自己返回就可以
        return true;
    }
    //创建菜单的时候 回调
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单渲染器 渲染写好的菜单布局，向menu对象中渲染菜单项
        Log.i("ContactsActivity","onCreateOptionsMenu被点击....");
        //找到  添加
        getMenuInflater().inflate(R.menu.menu_contacts,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
