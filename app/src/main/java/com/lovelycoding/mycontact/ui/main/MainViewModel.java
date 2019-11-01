package com.lovelycoding.mycontact.ui.main;

import android.content.ContentResolver;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lovelycoding.mycontact.datamodel.Repository;
import com.lovelycoding.mycontact.pojo.Contact;
import com.lovelycoding.mycontact.ui.adapter.MyContactAdapter;

import java.util.List;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG = "MainViewModel";

   // MutableLiveData<List<Contact>> liveDataContactList=new MutableLiveData<>();
    public MutableLiveData<List<Contact>> fetchContactList( ContentResolver contentResolver)
    {
        return Repository.getInstance().fetchContactList(contentResolver);

    }
}
