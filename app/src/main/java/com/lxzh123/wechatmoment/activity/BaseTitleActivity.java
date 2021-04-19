package com.lxzh123.wechatmoment.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.lxzh123.wechatmoment.R;

/**
 * description: 带标题栏的基础 Activity
 * author:      Created by lxzh on 2021/4/19.
 */
public abstract class BaseTitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initActionBar();
        initView();
        initData();
        initAction();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setCustomTitle(int textId) {
        setCustomTitle(getString(textId));
    }

    protected void setCustomTitle(String text) {
        ImageView imageView = findViewById(R.id.iv_title_back);
        imageView.setOnClickListener(view -> finish());

        TextView textView = findViewById(R.id.tv_title_text);
        textView.setText(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initAction() {

    }
}
