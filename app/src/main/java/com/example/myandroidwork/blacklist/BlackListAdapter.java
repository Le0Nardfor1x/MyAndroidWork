package com.example.myandroidwork.blacklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidwork.R;


import java.util.List;

public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.ViewHolder>{
    private List<BlackList> blackListItems;

    public BlackListAdapter(List<BlackList> blackListItems) {
        this.blackListItems = blackListItems;
    }

    @NonNull
    @Override
    public BlackListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_blacklist, parent, false);
        return new BlackListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackListAdapter.ViewHolder holder, int position) {
        BlackList blackList = blackListItems.get(position);
        holder.BlackListNameText.setText(blackList.getName());
        holder.BlackListNumberText.setText(blackList.getNumber());
    }

    @Override
    public int getItemCount() {
        return blackListItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView BlackListNameText;
        public TextView BlackListNumberText;


        public ViewHolder(View view) {
            super(view);
            BlackListNameText = view.findViewById(R.id.black_list_name_text);
            BlackListNumberText= view.findViewById(R.id.black_list_number_text);
        }
    }

}
