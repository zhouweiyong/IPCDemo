package com.nz.aidl_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        tv_msg.setText(msg);
    }
}
