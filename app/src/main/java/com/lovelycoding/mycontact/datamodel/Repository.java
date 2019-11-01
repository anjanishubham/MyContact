package com.lovelycoding.mycontact.datamodel;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lovelycoding.mycontact.pojo.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository mRepository;
    public static int limit=10;
    public static int offset=0;
    public static int pagerNumber=1;
    public static Repository getInstance() {

        if (mRepository == null) {
            mRepository=new Repository();
        }
        return mRepository;
    }

    public MutableLiveData<List<Contact>> fetchContactList(ContentResolver contentResolver) {
        MutableLiveData<List<Contact>> listMutableLiveData=new MutableLiveData<>();
        List<Contact> contactList=new ArrayList<>();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();

        Cursor contactCursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null ,null,ContactsContract.Contacts.DISPLAY_NAME +
                        " ASC LIMIT " + limit + " OFFSET " + offset);
        while(contactCursor.moveToNext()){
            Contact contact = new Contact();
            contact.setUserName(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setUserMobileNumber(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            String uri=(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));
            if (uri != null) {
                try {
                    contact.setUserImage(MediaStore.Images.Media.getBitmap(contentResolver,Uri.parse(uri)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            contactList.add(contact);

        }
        offset=limit*pagerNumber;
        pagerNumber++;
        contactCursor.close();
        contactList.add(null);// Adding the null value at last so we can show the loading cursor in recycler view
        listMutableLiveData.setValue(contactList);
        return listMutableLiveData;
    }
}
