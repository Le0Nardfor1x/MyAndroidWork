package com.example.myandroidwork;

import static android.Manifest.permission.READ_PHONE_STATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myandroidwork.callLog.CallLogActivity;
import com.example.myandroidwork.contacts.ContactsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int  PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button displayContactsBtn = findViewById(R.id.display_contacts_button);
        Button displayCallLogBtn = findViewById(R.id.display_call_log_button);
        displayContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestPermissions()){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ContactsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
        displayCallLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestPermissions()){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CallLogActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private boolean requestPermissions() {
        String[] permissions = {Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS,
                Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE};
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]),
                    PERMISSIONS_REQUEST_CODE);
        }
        else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            List<String> permissionList = Arrays.asList(permissions);
            if (permissionList.contains(Manifest.permission.READ_CALL_LOG)
                    && permissionList.contains(Manifest.permission.READ_CONTACTS)
                    && permissionList.contains(Manifest.permission.INTERNET)
                    && permissionList.contains(Manifest.permission.READ_PHONE_STATE)
                    && permissionList.contains(Manifest.permission.CALL_PHONE)
                    && grantResults.length > 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED){
                // 权限允许
            } else {
                // 权限不允许
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}