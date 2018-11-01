package com.nz.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.nz.aidl.NZhongAIDL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BootstrapButton btn_bind;
    private BootstrapEditText et_num1;
    private BootstrapEditText et_num2;
    private BootstrapEditText et_result;
    private BootstrapButton btn_suan;
    private NZhongAIDL nZhongAIDL;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            nZhongAIDL = NZhongAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            nZhongAIDL = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_bind = (BootstrapButton) findViewById(R.id.btn_bind);
        et_num1 = (BootstrapEditText) findViewById(R.id.et_num1);
        et_num2 = (BootstrapEditText) findViewById(R.id.et_num2);
        et_result = (BootstrapEditText) findViewById(R.id.et_result);
        btn_suan = (BootstrapButton) findViewById(R.id.btn_suan);

        btn_bind.setOnClickListener(this);
        btn_suan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind:
                bindService();
                break;
            case R.id.btn_suan:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String num1 = et_num1.getText().toString().trim();
        if (TextUtils.isEmpty(num1)) {
            Toast.makeText(this, "num1不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String num2 = et_num2.getText().toString().trim();
        if (TextUtils.isEmpty(num2)) {
            Toast.makeText(this, "num2不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            int rs = nZhongAIDL.add(Integer.parseInt(num1), Integer.parseInt(num2));
            et_result.setText(String.valueOf(rs));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.nz.aidl_server.RemoteService");
        intent.setComponent(new ComponentName("com.nz.aidl_server", "com.nz.aidl_server.RemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
