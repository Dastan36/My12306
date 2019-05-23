package com.neusoft.my12306.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.my12306.R;

public class PswActivity extends AppCompatActivity {
    Button btsavePas = null;
    EditText etPassWord = null;
    EditText etAgainPassWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw);

        //ActionBar  添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btsavePas = findViewById(R.id.btsavePas);
        etPassWord = findViewById(R.id.etPasword);
        etAgainPassWord = findViewById(R.id.etAgainPasword);
        btsavePas.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String pass1 = etPassWord.getText().toString();
                String pass2 = etAgainPassWord.getText().toString();
                //1
                if(TextUtils.isEmpty(pass1)){
                    //etPassWord.setError("请输入密码.");

                    //提示
                   // etPassWord.setError("请输入密码");
                   Toast.makeText(PswActivity.this,"请输入密码",Toast.LENGTH_LONG).show();

                    return;
                }
                if(TextUtils.isEmpty(pass2)){
//                    etAgainPassWord.setError("请再次输入密码");
                    //etAgainPassWord.setError("请重复输入密码.");
                    Toast.makeText(PswActivity.this,"请重复输入密码",Toast.LENGTH_SHORT).show();

                    return;
                }


                if(!pass1.equals(pass2)){
                    //etPassWord.setError("密码是否一致.");
                    return;
                }



                //保存到数据库中


            }
        });
    }
    /*
  监听菜单事件
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果点击的 是 返回菜单
        //R.id
        if(item.getItemId() == android.R.id.home){
            //finish当前 Activity
            finish();
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
