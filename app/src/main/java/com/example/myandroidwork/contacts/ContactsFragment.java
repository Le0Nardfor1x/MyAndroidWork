package com.example.myandroidwork.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidwork.R;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ContactsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView= view.findViewById(R.id.contacts_recycler_view);
        adapter = new ContactsAdapter(getContacts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private List<Contacts> getContacts() {
        List<Contacts> contactsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String number = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String address = getAddressFromContactNumber(getContext(),number);
//                    WeatherInfo weatherInfo = getWeatherFromAddress(getContext(),"360900");
//                    String weather = weatherInfo.getWeather();
//                    String temperature = weatherInfo.getTemperature();
                    Contacts contact = new Contacts(name,number,address,"weather","temperature");
                    contactsList.add(contact);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return contactsList;
    }

    @SuppressLint("Range")
    public String getAddressFromContactNumber(@NonNull Context context, String contactNumber) {
        String address = "";
        // 通过电话号码查询联系人数据
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(contactNumber));
        Cursor cursor = context.getContentResolver().query(lookupUri, null, null, null, null);

        while (cursor.moveToNext()) {
             String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));

            // 获取联系人地址
            Cursor addressCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.
                            StructuredPostal.CONTENT_URI, null, ContactsContract.
                    CommonDataKinds.StructuredPostal.CONTACT_ID + "=?", new String[]{contactId},
                    null);

            while (addressCursor.moveToNext()) {
                address = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.
                        CommonDataKinds.StructuredPostal.DATA));
            }
            addressCursor.close();
        }
        cursor.close();
        return address;
    }

//    public WeatherInfo getWeatherFromAddress(Context context,String cityId){
//        WeatherInfo weatherInfo = new WeatherInfo();
//        String myKey = "fa21569fda915aaa92c22736e7efb6d1";
//        String url = "https://restapi.amap.com/v3/weather/weatherInfo?" +
//                "key="+myKey+"&city="+cityId+"&extension=base";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                try {
//                    String jsonData = response.body().string();
//                    JSONObject json = new JSONObject(jsonData);
//                    JSONObject lives = json.getJSONObject("lives");
//                    String weather = json.getString("weather");
//                    String temperature = lives.getString("temperature");
//                    weatherInfo.setWeather(weather);
//                    weatherInfo.setTemperature(temperature);
//                    Log.i("weather",weather);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        return weatherInfo;
//    }
}