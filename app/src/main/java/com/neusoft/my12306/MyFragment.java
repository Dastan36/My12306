package com.neusoft.my12306;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neusoft.my12306.R;
import com.neusoft.my12306.my.AccountActivity;
import com.neusoft.my12306.my.ContactsActivity;
import com.neusoft.my12306.my.PswActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    ListView lvMyInfo=null;
    ListAdapter adapter=null;
    Button btnLogout=null;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //通过Id找listView
        lvMyInfo=  getActivity().findViewById(R.id.lvMyInfo);
        //写在Strings.xml中
        final String[] infos  = getResources().getStringArray(R.array.list_view_my);

        //数据
        // String infos[]={"我的联系人","我的账户","我的密码"};
//        String infos1[]= new String [100];
//        for (int i = 0; i <infos1.length ; i++) {
//            infos1[i]="条目"+i;
//        }

        //适配器
        adapter=new ArrayAdapter( getActivity(),android.R.layout.simple_list_item_activated_1
                ,infos);
        //将数据和LIStView捆绑起来
        lvMyInfo.setAdapter(adapter);

        //给ListView条目添加点击事件
        lvMyInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent=new Intent();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent.setClass( getActivity(), ContactsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass( getActivity(), AccountActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass( getActivity(), PswActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });

        btnLogout=  getActivity().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass( getActivity(),LoginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
                new MyLogOutAsyncTask().execute();//调用异步类

            }
        });

    }
    /**
     * 异步任务  Extends AsyncTask
     * Object, execute 入参类型----->doInBackground(参数类型)
     *
     * Object, 更新Progress 的类型
     *
     * Boolean  doInBackground 返回值类型----》onPostExecute(参数类型)
     */
    class MyLogOutAsyncTask extends AsyncTask<Object,Object,Boolean> {

        @Override
        protected Boolean doInBackground(Object... objects) {

            Boolean result=false;
            HttpClient client=new DefaultHttpClient();
            String uri="http://192.168.81.11:8080/ssm/logout";

            //访问网络资源，创建HttpPost/HttpGet对象
            HttpPost post=new HttpPost(uri);
            try {
                HttpResponse response=client.execute(post);
                //使用工具方法读取  response的流
                String json=EntityUtils.toString((HttpEntity) response.getEntity());

                //解析json
                Log.i("MyFragment","json:"+json);

                Gson gson = new GsonBuilder().create();

                //使用Gson解析 json
                HashMap objResult = gson.fromJson(json, HashMap.class);
                result = (Boolean) objResult.get("success");


                //解析json  {"success":true/false};     Gson

                result=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "提示消息", "处理中..", true, false);


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(progressDialog!=null){
                progressDialog.dismiss();
                Intent intent =new Intent();
                intent.setClass(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
            super.onPostExecute(aBoolean);
        }
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
            getActivity().finish();
        }
//        return super.onOptionsItemSelected(item);  true代表不给父级，自己返回就可以
        return true;
    }

}
