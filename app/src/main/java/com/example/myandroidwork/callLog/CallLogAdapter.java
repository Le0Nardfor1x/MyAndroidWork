package com.example.myandroidwork.callLog;

import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidwork.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {
    private List<CallLogItem> callLogItems;

    public CallLogAdapter(List<CallLogItem> callLogItems) {
        this.callLogItems = callLogItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_call_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogItem item = callLogItems.get(position);
        holder.nameTextView.setText(item.getName());
        holder.numberTextView.setText(item.getNumber());
        holder.dateTextView.setText(getDateString(item.getDate()));
        holder.typeImageView.setImageResource(getTypeImageResource(item.getType()));
        holder.addressTextView.setText(item.getAddress());
    }

    @Override
    public int getItemCount() {
        return callLogItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numberTextView;
        public TextView dateTextView;
        public ImageView typeImageView;
        public TextView addressTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_text_view);
            numberTextView = view.findViewById(R.id.number_text_view);
            dateTextView = view.findViewById(R.id.date_text_view);
            typeImageView = view.findViewById(R.id.type_image_view);
            addressTextView = view.findViewById(R.id.address_text_view);
        }
    }

    private String getDateString(long timestamp) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(timestamp));
    }

    private int getTypeImageResource(int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                return R.drawable.incoming_call;
            case CallLog.Calls.OUTGOING_TYPE:
                return R.drawable.outgoing_call;
            case CallLog.Calls.MISSED_TYPE:
            default:
                return R.drawable.missed_call;
        }
    }
}
