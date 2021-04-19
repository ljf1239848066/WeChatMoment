package com.lxzh123.wechatmoment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.adapter.CustomFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 微信主界面
 * author:      Created by lxzh on 2021/4/19.
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private final int TAB_COUNT = 4;
    private List<Fragment> tabFracments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * initialize view, findView, bind event listener
     */
    private void initView() {
        tabLayout = findViewById(R.id.main_tabs);
        viewPager = findViewById(R.id.tb_content);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setupWithViewPager(viewPager);
        ViewCompat.setElevation(tabLayout, 0);


        tabFracments = new ArrayList<>();
        tabFracments.add(new ChatFragment());//android.R.id.ic_menu_start_conversation
        tabFracments.add(new ContactFragment());//android.R.id.ic_menu_friendslist
        tabFracments.add(new FindFragment());//android.R.id.ic_menu_compass
        tabFracments.add(new MeFragment());//android.R.id.ic_contact_picture

        CustomFragmentPagerAdapter pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), tabFracments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.getTabAt(0).setText(R.string.main_tab_chat).setIcon(R.drawable.main_tab_wechat_bg);
        tabLayout.getTabAt(1).setText(R.string.main_tab_contact).setIcon(R.drawable.main_tab_contact_bg);
        tabLayout.getTabAt(2).setText(R.string.main_tab_find).setIcon(R.drawable.main_tab_find_bg);
        tabLayout.getTabAt(3).setText(R.string.main_tab_me).setIcon(R.drawable.main_tab_me_bg);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_tab_bar_pressed));
        tabLayout.setTabTextColors(R.color.main_tab_bar_normal, Color.parseColor("#1FA015"));
        tabLayout.setTabTextColors(getResources().getColor(R.color.main_tab_bar_normal), getResources().getColor(R.color.main_tab_bar_pressed));
    }
}
