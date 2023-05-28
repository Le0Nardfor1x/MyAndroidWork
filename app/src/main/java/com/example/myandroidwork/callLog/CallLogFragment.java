package com.example.myandroidwork.callLog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidwork.R;

import java.util.ArrayList;
import java.util.List;


public class CallLogFragment extends Fragment {
    private RecyclerView recyclerView;
    private CallLogAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        recyclerView = view.findViewById(R.id.call_log_recycler_view);
        adapter = new CallLogAdapter(getCallLogs());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private List<CallLogItem> getCallLogs() {
        List<CallLogItem> callLogItems = new ArrayList<>();
        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.GEOCODED_LOCATION
        };
        String sortOrder = CallLog.Calls.DATE + " DESC";

        try (Cursor cursor = getContext().getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    @SuppressLint("Range")
                    String name = getNameForNumber(getContext(),number);
                    @SuppressLint("Range")
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    @SuppressLint("Range")
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    @SuppressLint("Range")
                    String address = getPhoneNumberLocationFromCallLog(number);
                    CallLogItem item = new CallLogItem(name, number, type, date);
                    //Log.i("info",item.getNumber()+" "+address);
                    item.setAddress(address);
                    callLogItems.add(item);
                } while (cursor.moveToNext());
            }
        }
        return callLogItems;
    }

@SuppressLint("Range")
private String getPhoneNumberLocationFromCallLog(String phoneNumber) {
    String[] projection = {CallLog.Calls.GEOCODED_LOCATION};
    String selection = CallLog.Calls.NUMBER + "=?";
    String[] selectionArgs = {phoneNumber};
    Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection,
            selection, selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
    if (cursor != null && cursor.moveToFirst()) {
        String location = cursor.getString(cursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION));
        cursor.close();
        return location;
    }
    return "未知";
}

    @SuppressLint("Range")
    private String getNameForNumber(Context context, String phoneNumber) {
        String name = null;
        String[] projection = new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup.NUMBER
        };
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = context.getContentResolver().query(
                uri,
                projection,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
            cursor.close();
        }
        return name;
    }
}