package com.example.myandroidwork.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myandroidwork.R;
import com.example.myandroidwork.blacklist.BlackListActivity;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getContactsFragment();

        Button contactsBackBtn = findViewById(R.id.contacts_back_button);
        contactsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button contactsToBlacklistBtn = findViewById(R.id.contacts_to_blacklist_button);
        contactsToBlacklistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, BlackListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getContactsFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contacts_view, new ContactsFragment());
        fragmentTransaction.commit();
    }
}