package com.nz.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.nz.aidl.NZhongAIDL;

/**
 * 通过本服务介绍客户端的消息
 */
public class RemoteService extends Service {
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new NZhongAIDL.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e("zwy", "收到了来自客户端的请求" + num1 + "+" + num2);
            String msg = "收到了来自客户端的请求" + num1 + "+" + num2 + "，返回结果为" + (num1 + num2);
            Intent intent2 = new Intent(RemoteService.this, MessageActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("msg", msg);
            getApplication().startActivity(intent2);
            return num1 + num2;
        }

    };
}
