package com.neusoft.my12306;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    private Button btn = null;
    private EditText edUserName = null;
    private EditText edPassword = null;
    private TextView tvForget = null;
    private CheckBox cbCheck = null;
    AlertDialog dialog = null;
    String userName;
    String password;
    ProgressDialog pDialog;
    //UI线程对象
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //取消dialog
            pDialog.dismiss();
            int what=msg.what;
            if(what==1){

                int result=msg.arg1;
                if(result==1){
                    Toast.makeText(LoginActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    if ("admin".equals(edUserName.getText().toString())
                            && "123456".equals(edPassword.getText().toString())) {

                        if (cbCheck.isChecked()) {

                            //把登录的用户名密码存储到手机端
                            SharedPreferences shard = LoginActivity.this.getSharedPreferences("login_user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = shard.edit();
                            edit.putString("userName", userName);
                            edit.putString("password", password);//存到内存
                            edit.commit();//提交存到硬盘

                        }

                        //隐式意图
                        Intent intent1 = new Intent();
//                   intent1.setAction("action.MyActivity");
//                   intent1.addCategory("category.MyActivity");
                        intent1.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    } else {
//                   edUserName.setError("用户名或密码错误");
//                   edUserName.requestFocus();
                        dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("错误消息").
                                setMessage("用户名或密码错误").setPositiveButton("确定", null).
                                setNegativeButton("取消", null).create();
                        dialog.show();
                        edUserName.setText(null);
                        edPassword.setText(null);


                    }
                }else {
                    Toast.makeText(LoginActivity.this,"失败",Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(LoginActivity.this,"访问网络失败，请检查网络...",Toast.LENGTH_SHORT).show();

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //找到按钮添加事件
        //1   通过id找到按钮
        btn = findViewById(R.id.BtnLogin);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        tvForget = findViewById(R.id.tvForget);
        cbCheck = findViewById(R.id.cbCheck);
        //设置文字内容
        tvForget.setText(Html.fromHtml("<a href=\"http://baidu\">忘记密码？</a>"));
        //设置文本框鼠标行为
        tvForget.setMovementMethod(LinkMovementMethod.getInstance());


        //2  为按钮添加事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               System.out.println("按钮被点击。。。。");
                //想要跳转到MAinActivity
                //需要Intent 意图
                /** Intent intent=new Intent();
                 //代表从LoginActivity.this ------------》跳到MainActivity.class
                 intent.setClass(LoginActivity.this,MyActivity.class);
                 //高速启动哪一个Activity
                 startActivity(intent);
                 //执行完就自己结束自己，后边的Activity不能返回**/
                userName = edUserName.getText().toString();
                password = edPassword.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    edUserName.setError("请输入用户名");
                    edUserName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edPassword.setError("请输入密码");
                    edPassword.requestFocus();
                    return;
                }
                pDialog = ProgressDialog.show(LoginActivity.this, null, "正在加载中...", true, false);



                //Android UI线程不允许直接发出网络请求，
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        accessNet(userName, password);//访问网络
                    }
                }.start();
                Log.i("LoginActivity", "onClick:" + userName);
                Log.i("LoginActivity", "onClick:" + password);

            }
        });
    }

    /**
     * 访问网络 判断用户名密码，是否允许登录
     * HttpClient
     * <p>
     * HttpComponents
     */
    public void accessNet(String userName, String password) {
        //http://192.168.81.11:8080/ssm/validate_login?userName=admin1111&password=123456

        HttpClient client = new DefaultHttpClient();

        String uri = "http://192.168.81.11:8080/ssm/validate_login";
        //访问网络资源，创建HttpPost/HttpGet对象
        HttpPost post = new HttpPost(uri);
        Message msg = handler.obtainMessage();
        try {
            List parameters = new ArrayList();
            parameters.add(new BasicNameValuePair("userName", userName));
            parameters.add(new BasicNameValuePair("password", password));

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            //使用setEntity方法设置请求参数
            post.setEntity(formEntity);
            //以execute(post)post发起网络请求
            HttpResponse response = client.execute(post);
//           //getEntity()方法返回响应信息，进行处理

            if (response.getStatusLine().getStatusCode() == 200) {
//                String xml = EntityUtils.toString(response.getEntity());
//                System.out.println("xml :"+xml);

                //发送一个消息到UI线程，让UI线程自己处理
                String result = analyXml(response.getEntity().getContent());


                CookieStore cs = ((DefaultHttpClient) client).getCookieStore();
                List<Cookie> cookies = cs.getCookies();
               //JSESSIONID
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("JSESSIONID")){
                        System.out.println("cookie.getValue():"+cookie.getValue());
                        break;
                    }
                }


                msg.arg1 = Integer.parseInt(result);
                msg.what=1;

                //解析xml                    参数是InputStream
                System.out.println("result" + result);
            }


        } catch (IOException e) {
            msg.what=2;
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            msg.what=2;
            e.printStackTrace();
        }
       //访问网络后
        handler.sendMessage(msg);
    }

    /***
     *
     *  解析xml
     *
     * <?xml version=\"1.0\" encoding=\"utf-8\"?>
     *  <data>
     * 	    <result>1</result>
     *  </data>
     * @param is
     * @return
     */

    private String analyXml(InputStream is) throws XmlPullParserException, IOException, XmlPullParserException {

        String result = "0";
        //创建解析器
        XmlPullParser parser = Xml.newPullParser();


        //设置输入内容
        parser.setInput(is, "utf-8");


        //通过事件循环
        int eventType = parser.getEventType();

        System.out.println("eventType:" + eventType);

        while (true) {
            eventType = parser.next();
            System.out.println("eventType:" + eventType);

            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("result")) {
//                parser.next()
//                parser.getText()
                result = parser.nextText();
                System.out.println("result :" + result);
                break;
            }

        }
        return result;
    }
}

