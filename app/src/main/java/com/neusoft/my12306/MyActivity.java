package com.neusoft.my12306;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.neusoft.my12306.my.AccountActivity;
import com.neusoft.my12306.my.ContactsActivity;
import com.neusoft.my12306.my.PswActivity;

public class MyActivity extends AppCompatActivity{
    ListView lvMyInfo=null;
    ListAdapter adapter=null;
    Button btnLogout=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //ActionBar  添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //通过Id找listView
        lvMyInfo= findViewById(R.id.lvMyInfo);
        //写在Strings.xml中
        final String[] infos  = getResources().getStringArray(R.array.list_view_my);

        //数据
       // String infos[]={"我的联系人","我的账户","我的密码"};
//        String infos1[]= new String [100];
//        for (int i = 0; i <infos1.length ; i++) {
//            infos1[i]="条目"+i;
//        }

       //适配器
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1
                ,infos);
        //将数据和LIStView捆绑起来
        lvMyInfo.setAdapter(adapter);

        //给ListView条目添加点击事件
        lvMyInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent  intent=new Intent();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent.setClass(MyActivity.this,ContactsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MyActivity.this,AccountActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MyActivity.this,PswActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });

        btnLogout= findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });



//        //找到控件
//        Button btnContact=findViewById(R.id.btnContact);
//        Button btnAccount=findViewById(R.id.btnAccount);
//        Button btnPsw=findViewById(R.id.btnPsw);
//          Button btnLogout= findViewById(R.id.btnLogout);

        //添加点击事件
//        MyOnclickListener myOnclickListener=new MyOnclickListener();

//        btnContact.setOnClickListener(myOnclickListener);
//        btnAccount.setOnClickListener(myOnclickListener);
//        btnPsw.setOnClickListener(myOnclickListener);
//        btnLogout.setOnClickListener(myOnclickListener);
//
//    }
//    public class MyOnclickListener implements View.OnClickListener{
//
//        Intent intent=new Intent();
//        @Override
//        public void onClick(View v) {
//            if(R.id.btnContact==v.getId()){
//                //Log.i("MyActivity", "onClick: 我的联系人");
//                intent.setClass(MyActivity.this, ContactsActivity.class);
//                startActivity(intent);
//            }else  if(R.id.btnAccount==v.getId()){
//                //Log.i("MyActivity", "onClick:我的账户 ");
//                intent.setClass(MyActivity.this, AccountActivity.class);
//                startActivity(intent);
//            }else  if(R.id.btnPsw==v.getId()){
//               // Log.i("MyActivity", "onClick:我的密码 ");
//                intent.setClass(MyActivity.this, PswActivity.class);
//                startActivity(intent);
//            }else {
//                intent.setClass(MyActivity.this,LoginActivity.class);
//                startActivity(intent);
//                //Log.i("MyActivity", "onClick:退出登录 ");
//            }
//
//        }
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
        }
//        return super.onOptionsItemSelected(item);  true代表不给父级，自己返回就可以
        return true;
    }
}
