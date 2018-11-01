package com.nz.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 通过Content Provider实现
 * 1，进程内通信
 * 2，进程间通信
 * <p>
 * 对进程间通信来说，本app开放uri给其他app访问
 * <p>
 * https://blog.csdn.net/carson_ho/article/details/76101093
 *
 * 步骤：
 * 1，
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BootstrapButton btn_same;
    private BootstrapButton btn_diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        btn_same = (BootstrapButton) findViewById(R.id.btn_same);
        btn_diff = (BootstrapButton) findViewById(R.id.btn_diff);

        btn_same.setOnClickListener(this);
        btn_diff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_same:
                startActivity(new Intent(this,SameProcessActivity.class));
                break;
            case R.id.btn_diff:
                startActivity(new Intent(this,DifferenceProcessActivity.class));
                break;
        }
    }
}
