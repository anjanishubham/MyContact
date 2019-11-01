package com.lovelycoding.mycontact.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.mycontact.databinding.LoadMoreLayoutBinding;

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    LoadMoreLayoutBinding loadMoreLayoutBinding;
    public LoadMoreViewHolder(@NonNull LoadMoreLayoutBinding itemView) {
        super(itemView.getRoot());
        this.loadMoreLayoutBinding=itemView;
    }
}
