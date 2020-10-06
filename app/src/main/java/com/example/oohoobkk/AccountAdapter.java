package com.example.oohoobkk;

import android.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<String> accountList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;

        public ViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.txt_item);
        }
    }
    public AccountAdapter(List<String> list) {
        accountList = list;
    }

    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(accountList.get(position));
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

}
