package com.nz.cp_b;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.ArrayList;

/**
 * https://blog.csdn.net/carson_ho/article/details/76101093
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BootstrapEditText et_id;
    private BootstrapEditText et_content;
    private BootstrapButton btn_add;
    private ListView lv_same;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ContentResolver resolver;
    private Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");
    private BootstrapButton btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        et_id = (BootstrapEditText) findViewById(R.id.et_id);
        et_content = (BootstrapEditText) findViewById(R.id.et_content);
        btn_add = (BootstrapButton) findViewById(R.id.btn_add);
        lv_same = (ListView) findViewById(R.id.lv_same);
        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.activity_list_item, android.R.id.text1, list);
        lv_same.setAdapter(adapter);
        btn_add.setOnClickListener(this);


        resolver = getContentResolver();
        btn_add.setOnClickListener(this);
        btn_refresh = (BootstrapButton) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                submit();
                break;
            case R.id.btn_refresh:
                query();
                break;
        }
    }

    private void submit() {
        // validate
        String id = et_id.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "id不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "content不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("name", content);


        // 获取ContentResolver

        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user, values);

        query();

    }


    //查询全部数据
    public void query() {
        list.clear();
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id", "name"}, null, null, null);
        while (cursor.moveToNext()) {
            System.out.println("query book:" + cursor.getInt(0) + " " + cursor.getString(1));
            // 将表中数据全部输出
            list.add(cursor.getInt(0) + " " + cursor.getString(1));
        }
        cursor.close();
        // 关闭游标
        adapter.notifyDataSetChanged();
    }
}
