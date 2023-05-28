package com.example.myandroidwork.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidwork.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contacts> contactsList;

    public ContactsAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contacts contact = contactsList.get(position);
        holder.contactNameText.setText(contact.getName());
        holder.contactNumberText.setText(contact.getNumber());
        holder.contactAddressText.setText(contact.getAddress());
        holder.contactWeatherText.setText(contact.getWeather());
        holder.contactTemperatureText.setText(contact.getTemperature());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactNameText;
        public TextView contactNumberText;
        public TextView contactAddressText;
        public TextView contactWeatherText;
        public TextView contactTemperatureText;


        public ViewHolder(View view) {
            super(view);
            contactNameText = view.findViewById(R.id.contact_name_text);
            contactNumberText= view.findViewById(R.id.contact_number_text);
            contactAddressText = view.findViewById(R.id.contact_address_text);
            contactWeatherText = view.findViewById(R.id.contact_weather_text);
            contactTemperatureText = view.findViewById(R.id.contact_temperature_text);

        }
    }

}
