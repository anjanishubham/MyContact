package com.lovelycoding.mycontact.ui.main;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.lovelycoding.mycontact.BuildConfig;
import com.lovelycoding.mycontact.R;
import com.lovelycoding.mycontact.databinding.MainFragmentBinding;
import com.lovelycoding.mycontact.pojo.Contact;
import com.lovelycoding.mycontact.ui.adapter.MyContactAdapter;
import com.lovelycoding.mycontact.ui.adapter.RecycleViewItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private MainFragmentBinding dataBinding;
    private RecyclerView mRecycleView;
    private MyContactAdapter mAdapter;
    private List<Contact> contactList = new ArrayList<>();
    public static boolean isLoading;
    private int currentItem,totalItem,scrollOutItems;
    public static final int CONTACTS_READ_PERMISSION=10;
    public ContentResolver contentResolver;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setRetainInstance(true);
        contentResolver=getActivity().getContentResolver();
        mRecycleView=dataBinding.rvContact;
        mAdapter=new MyContactAdapter();
        initRecycleView();

        // scroll Listener
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager= (LinearLayoutManager) mRecycleView.getLayoutManager();

                currentItem=layoutManager.getChildCount();
                totalItem=layoutManager.getItemCount();
                scrollOutItems=layoutManager.findFirstVisibleItemPosition();
                if (isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == contactList.size() - 1) {
                    loadMoreContact();
                }

            }
        });

        return dataBinding.getRoot();

    }

    private void loadMoreContact() {
        while (contactList.contains(null))
        {
            contactList.remove(null);
        }
        //contactList.remove(contactList.size()-1);

       fetchContact();
    }
    private void initRecycleView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.addItemDecoration(new RecycleViewItemDecorator(10));
        mAdapter.setContactList(contactList);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        if(getRuntimePermission()){
            fetchContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CONTACTS_READ_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchContact();
                } else {
                    enablePermissionFromSetting();
                }
            }
        }
    }
        // User deny the permission the it will open the app setting permission app
    private void enablePermissionFromSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please Allow to access the Contact ");
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivity(intent);

            }
        }).setCancelable(false);
        builder.create();
        builder.show();

    }
// Fetching the contact from Database
    private void fetchContact() {
        mViewModel.fetchContactList(contentResolver).observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                if (contacts.size() >= 0) {
                    contactList.addAll(contacts);
                    mAdapter.setContactList(contactList);
                    mAdapter.notifyDataSetChanged();
                    if (contacts.size() < 10) {
                        contactList.remove(null);// it fetch all the contact form Database for remove the null
                        Toast.makeText(getContext(), "No more Contact !!", Toast.LENGTH_SHORT).show();
                        isLoading=false;

                    }
                    else {
                        isLoading=true;
                    }
                }
            }
        });

    }

        // checking runtime permission
    private boolean getRuntimePermission() {
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                Log.d(TAG, "getRuntimePermission: ");
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},CONTACTS_READ_PERMISSION);
                return false;
            }
            else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},CONTACTS_READ_PERMISSION);
                return false;

            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(contactList.contains(null))
            contactList.remove(null);

    }
}
