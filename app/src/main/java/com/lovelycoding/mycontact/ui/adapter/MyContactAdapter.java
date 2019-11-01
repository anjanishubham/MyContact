package com.lovelycoding.mycontact.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.mycontact.R;
import com.lovelycoding.mycontact.databinding.ContactItemLayoutBinding;
import com.lovelycoding.mycontact.databinding.LoadMoreLayoutBinding;
import com.lovelycoding.mycontact.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> contactList;
    public static final int VIEW_TYPE_ITEM=0;
    public static final int VIEW_TYPE_LOAD_MORE=1;

    public MyContactAdapter() {
        contactList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==VIEW_TYPE_ITEM) {
            ContactItemLayoutBinding itemLayoutBinding = DataBindingUtil.inflate
                    (LayoutInflater.from(parent.getContext()), R.layout.contact_item_layout, parent, false);
            return new MyContactViewHolder(itemLayoutBinding);
        }
        LoadMoreLayoutBinding loadMoreLayoutBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.load_more_layout, parent, false);
        return new LoadMoreViewHolder(loadMoreLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyContactViewHolder)
         ((MyContactViewHolder)holder).itemLayoutBinding.setContactModel(contactList.get(position));
        else if(holder instanceof LoadMoreViewHolder)
        {
            Log.d(TAG, "onBindViewHolder:loader icon ");
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return contactList.get(position)==null ? VIEW_TYPE_LOAD_MORE:VIEW_TYPE_ITEM;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

}
