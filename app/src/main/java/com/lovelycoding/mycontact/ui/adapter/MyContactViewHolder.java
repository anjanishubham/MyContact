package com.lovelycoding.mycontact.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.mycontact.databinding.ContactItemLayoutBinding;

public class MyContactViewHolder extends RecyclerView.ViewHolder {
    ContactItemLayoutBinding itemLayoutBinding;
    public MyContactViewHolder(@NonNull ContactItemLayoutBinding itemView) {
        super(itemView.getRoot());
        itemLayoutBinding=itemView;

    }
}
