package com.neusoft.my12306.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neusoft.my12306.R;
import com.neusoft.my12306.domain.Account;
import com.neusoft.my12306.utils.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    private ListView lvMyAccount;
    List<Map<String,Object>> data=null;
    private SimpleAdapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //ActionBar  添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //找到id
        lvMyAccount=findViewById(R.id.lvMyAccount);

        data=new ArrayList<Map<String,Object>>();
        int res = R.layout.item_contacts_edit_layout;

        String[] from = {"title","value","src"};
        int[] to = {R.id.tvTitle,R.id.tvValue,R.id.tvForget};
        adapter = new SimpleAdapter(this,data,res,from,to);
        lvMyAccount.setAdapter(adapter);

        //通过网络查询data，Account


    }

    @Override
    protected void onResume() {
        super.onResume();
        new QueryAccountInfoTask().execute();
    }

      ProgressDialog dialog=null;

    class QueryAccountInfoTask extends AsyncTask<String,String, Account> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=ProgressDialog.show(AccountActivity.this,"","查询中，请稍后",false,false);
        }

        @Override
        protected Account doInBackground(String... strings) {

            Account account=null;

            HttpClient client=new DefaultHttpClient();
            String uri= Constants.HOST+Constants.ACCOUNT_INFO;
            HttpPost post=new HttpPost(uri);

            try {
                HttpResponse response=client.execute(post);

                if(response.getStatusLine().getStatusCode()==200){
                    String json =  EntityUtils.toString(response.getEntity());
                    Gson gson = new GsonBuilder().create();
                    account = gson.fromJson(json,Account.class);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return account;
        }

        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);
            if(dialog!=null){
                dialog.dismiss();
            }
            //数据
            Map row1=new HashMap();
            row1.put("title","用户名");
            row1.put("value",account.getUserName());

            Map row2=new HashMap();
            row2.put("title","姓名");
            row2.put("value",account.getDisplayname());


            Map row3=new HashMap();
            row3.put("title","证件类型");
            row3.put("value",account.getIdType());


            Map row4=new HashMap();
            row4.put("title","证件号码");
            row4.put("value",account.getIdNo());

            Map row5=new HashMap();
            row5.put("title","乘客类型");
            row5.put("value",account.getPersonType());
            row5.put("src",R.drawable.forward_25);

            Map row6 = new HashMap();
            row6.put("title","电话");
            row6.put("value",account.getTelphone());
            row6.put("src",R.drawable.forward_25);

            data.add(row1);
            data.add(row2);
            data.add(row3);
            data.add(row4);
            data.add(row5);
            data.add(row6);
            adapter.notifyDataSetChanged();
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
        if(item.getItemId() == android.R.id.home) {
            //finish当前 Activity
            finish();
        }
//        return super.onOptionsItemSelected(item);  true代表不给父级，自己返回就可以
        return true;
    }
}
