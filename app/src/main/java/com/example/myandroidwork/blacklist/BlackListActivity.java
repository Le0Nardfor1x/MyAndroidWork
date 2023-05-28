package com.example.myandroidwork.blacklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myandroidwork.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlackListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BlackListAdapter mAdapter;
    private List<BlackList> mBlackList;
    private BlackListDBHelper mDBHelper;

    private TelephonyManager mTelephonyManager;
    private PhoneStateListener mPhoneStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);


        mBlackList = new ArrayList<>();
        mDBHelper = new BlackListDBHelper(this);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //将黑名单数据库中的数据加入到mBlackList中
        Cursor cursor = db.query(BlackListDBHelper.TABLE_BLACKLIST,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            int id = cursor.getInt(cursor.getColumnIndex(BlackListDBHelper.COLUMN_ID));
            @SuppressLint("Range")
            String name = cursor.getString(cursor.getColumnIndex(BlackListDBHelper.COLUMN_NAME));
            @SuppressLint("Range")
            String number = cursor.getString(cursor.getColumnIndex(BlackListDBHelper.COLUMN_NUMBER));
            BlackList blacklist = new BlackList(id, name, number);
            mBlackList.add(blacklist);
        }
        cursor.close();

        //添加黑名单数据
        EditText blackListInputName = findViewById(R.id.black_list_input_name);
        Button blackListAddBtn = findViewById(R.id.black_list_add_btn);
        blackListAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = blackListInputName.getText().toString();
                String number = getNumberFromName(getBaseContext(),name);
                if (!name.isEmpty() && !number.isEmpty()) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(BlackListDBHelper.COLUMN_NAME, name);
                    values.put(BlackListDBHelper.COLUMN_NUMBER, number);
                    long id = db.insert(BlackListDBHelper.TABLE_BLACKLIST, null, values);
                    db.close();
                    BlackList blacklist = new BlackList((int) id, name, number);
                    mBlackList.add(blacklist);
                    Toast.makeText(BlackListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BlackListActivity.this, "姓名和号码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });




        //删除黑名单数据
        Button blackListDisplayBtn = findViewById(R.id.black_list_delete_btn);
        blackListDisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExisted = false;
                int position = 0;
               String name = blackListInputName.getText().toString();
                for (int i = 0; i < mBlackList.size(); i++) {
                    if(mBlackList.get(i).getName().equals(name)){
                        isExisted = true;
                        position = i;
                    }
                }
                if(isExisted){
               SQLiteDatabase db = mDBHelper.getWritableDatabase();
               db.delete(BlackListDBHelper.TABLE_BLACKLIST,
                       BlackListDBHelper.COLUMN_NAME+"='"+name+"'",null);
               db.close();
               mBlackList.remove(position);
                Toast.makeText(getBaseContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(),"黑名单中不存在此人",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //返回通讯录界面
        Button blackListBackBtn = findViewById(R.id.black_list_back_btn);
        blackListBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //设置屏蔽来电服务
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    // 判断来电号码是否在黑名单中
                    if (isBlocked(db,phoneNumber)) {
                        // 挂断来电
                        disconnectCall();
                    }
                }
            }
        };
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);


        mRecyclerView = findViewById(R.id.black_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mAdapter = new BlackListAdapter(mBlackList);
        mRecyclerView.setAdapter(mAdapter);

    }

    //在Activity销毁时停止监听来电状态的变化，以避免在Activity被销毁后仍然保持监听状态。
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    @SuppressLint("Range")
    public String getNumberFromName(Context context, String name) {
        String phoneNumber = null;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                new String[] { name },
                null);

        if (cursor != null && cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }
        return phoneNumber;
    }

    private boolean isBlocked(SQLiteDatabase db,String phoneNumber) {
        // 查询数据库，判断电话号码是否在黑名单中
        Cursor cursor = db.query(BlackListDBHelper.TABLE_BLACKLIST,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            String number = cursor.getString(cursor.getColumnIndex(BlackListDBHelper.COLUMN_NUMBER));
            if(number.equals(phoneNumber)){
                return true;
            }
        }
        cursor.close();
        return false;
    }
    private void disconnectCall() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm);
            Class<?> telephonyServiceClass = Class.forName(telephonyService.getClass().getName());
            Method endCallMethod = telephonyServiceClass.getDeclaredMethod("endCall");
            endCallMethod.invoke(telephonyService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}