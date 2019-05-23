package com.neusoft.my12306.my;

import android.app.AppComponentFactory;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.neusoft.my12306.LoginActivity;
import com.neusoft.my12306.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactEditActivity extends AppCompatActivity {
    SimpleAdapter adapter=null;
    ListView  lvMyContactEdit=null;
    List<Map<String,Object>> data=null;
    //ActionBar  添加返回按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
      //ActionBar  添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

       //获取intent
        Intent intent=getIntent();
        Map person=(Map) intent.getSerializableExtra("person");
        /*
           people.put("pname","李茜"+i+":学生");
            people.put("pID","身份证：130183199805151567");
            people.put("pTel","电话：18845121275");
         */
       String name= (String) person.get("pname");
       String xm=name.split(":")[0];
       String lx=name.split(":")[1];

       String id= (String) person.get("pID");
       String sfz=id.split(":")[0];
       String hm=id.split(":")[1];
       String pTel= (String) person.get("pTel");
        String tel = pTel.split(":")[1];

        lvMyContactEdit = findViewById(R.id.lvMyContactEdit);
        data=new ArrayList<Map<String,Object>>();
        Map row1=new HashMap();
        row1.put("title","姓名");
        row1.put("value",xm);
        row1.put("src",R.drawable.forward_25);
        data.add(row1);

        Map row2=new HashMap();
        row2.put("title","证件类型");
        row2.put("value",sfz);
        data.add(row2);

        Map row3=new HashMap();
        row3.put("title","证件号码");
        row3.put("value",hm);
        data.add(row3);


        Map row4=new HashMap();
        row4.put("title","乘客类型");
        row4.put("value",lx);
        row4.put("src",R.drawable.forward_25);
        data.add(row4);

        Map row5=new HashMap();
        row5.put("title","电话");
        row5.put("value",tel);
        row5.put("src",R.drawable.forward_25);
        data.add(row5);
        adapter=new SimpleAdapter(this,data,R.layout.item_contacts_edit_layout,
                                     new String[]{"title","value","src"},
                                     new int[]{R.id.tvTitle,R.id.tvValue, R.id.ivforword});

        lvMyContactEdit.setAdapter(adapter);
        //给条目添加点击事件
        lvMyContactEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        editName();
                        break;
                    case 3:
                        editType();
                        break;
                    case 4:
                        editTel();
                        break;

                }
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
    private void editName(){

        Map map = data.get(0);
        String xm= (String) map.get("value");

        //创建可编辑文本框
        final EditText view =new EditText(this);
        view.setText(xm);
        view.selectAll();//设置文本内容全选

//        view.setError("用户名不能为空");
       // Log.i("ContactEditActivity", name);
        Dialog d= new AlertDialog.Builder(this)
                .setIcon(R.drawable.administrator_25)
                .setTitle("请修改您的姓名")
                //.setMessage("确定退出吗？")
                 .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //将修改的数据同步到listView中，将EditText 的文本 设置到  data
                        Map<String, Object> people = data.get(0);
                        String name=view.getText().toString();
                        if(TextUtils.isEmpty(name)){
                            //设置dialog不能没有  并且给出提示
                           view.setError("用户名不能为空");
//                            Toast.makeText(ContactEditActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                            //对话框不让他消失
                            setClosable(dialog,false);
                            return;

                        }else {
                            //dialog可关闭
                            setClosable(dialog,true);
                            //设置新的值
                            people.put("value",name);

                            //重新绑定listview和数据
                            adapter.notifyDataSetChanged();

                        }


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setClosable(dialog,true);//设置dialog可关闭状态
                    }
                })
                .show();


    }
    /**
     * 通过 JAVA反射
     * @param dialog
     * @param b
     */
    private void setClosable(DialogInterface dialog, boolean b) {
        Field field;
        try {
            field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, b);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改乘客类型
     */

    int  checkeditem=-1;
    private void editType(){

       // Log.i("ContactEditActivity", "editType: ");
        final Map map = data.get(3);
        String lx= (String) map.get("value");
        final String[] items = new String[]{"成人", "儿童", "学生", "其他"};
        if(lx.contains("成人")) {
            checkeditem=0;
        }else if(lx.contains("儿童")) {
            checkeditem=1;
        }else if(lx.contains("学生")) {
            checkeditem=2;
        }else{
            checkeditem=3;
        }
        Dialog d= new AlertDialog.Builder(this)
                .setIcon(R.drawable.administrator_25)
                .setTitle("请选择乘客类型")
                .setSingleChoiceItems(items, checkeditem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Map map1=data.get(3);
                       map.put("value",items[which]);
                       //适配器进行重新的绑定
                        adapter.notifyDataSetChanged();
//                        Log.i("ContactsEditActivity","乘客类型被选中");
                        dialog.dismiss();//主动关闭
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setClosable(dialog,true);//设置dialog可关闭状态
                    }
                })
                .show();





    }
    private void editTel(){
        Log.i("ContactEditActivity", "editTel: ");
        Map map = data.get(4);
        String name= (String) map.get("value");

        //创建可编辑文本框
        final EditText view =new EditText(this);
        view.setText(name);
        view.selectAll();
        // Log.i("ContactEditActivity", name);
        Dialog d = new AlertDialog.Builder(this)
                .setIcon(R.drawable.administrator_25)
                .setTitle("请修改您的电话号")
                //.setMessage("确定退出吗？")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将修改的数据同步到listView中

                        Map<String, Object> people = data.get(4);
                        people.put("value",view.getText());
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
