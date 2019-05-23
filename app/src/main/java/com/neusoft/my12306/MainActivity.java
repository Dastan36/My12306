
package com.neusoft.my12306;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    TabLayout supportTabLayout=null;
    private ViewPager vp;
    List<Fragment> fragments=new ArrayList<Fragment>();
    List<CharSequence> tiitles=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar bar=getSupportActionBar();
//        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        bar.newTab().setText("车票");
//        bar.newTab().setText("订单");
//        bar.newTab().setText("我的");

        supportTabLayout= findViewById(R.id.supportTabLayout);

        vp=findViewById(R.id.vp);

        supportTabLayout.addTab(supportTabLayout.newTab().setText("车票").setIcon(R.mipmap.ic_launcher));
        supportTabLayout.addTab(supportTabLayout.newTab().setText("订单").setIcon(R.mipmap.ic_launcher));
        supportTabLayout.addTab(supportTabLayout.newTab().setText("@我的").setIcon(R.mipmap.ic_launcher));

        fragments.add(new TicketsFragment());
        fragments.add(new OrderFragment());
        fragments.add(new MyFragment());

        tiitles.add("车票");
        tiitles.add("订单");
        tiitles.add("@我的");


        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return  tiitles.get(position);
            }


        });
        //将tabLayout 和 ViewPager一起使用
        supportTabLayout.setupWithViewPager(vp);

    }
    long startTime = 0; //1500000001     1500000002


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - startTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                startTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        if(intent !=null){
            String msg=intent.getStringExtra("mag");
            if(msg!=null){
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }

    }
}
