package com.lovelycoding.mycontact.pojo;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.lovelycoding.mycontact.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Contact {
    private String userName;
    private String userMobileNumber;
    private Bitmap userImage;

    public Contact() {
    }

    public Contact(String userName, String userMobileNumber) {
        this.userName = userName;
        this.userMobileNumber = userMobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    @BindingAdapter("userImage")
    public static void setUserImage(CircleImageView imageView, Bitmap userImage){

        if (userImage != null) {
            imageView.setImageBitmap(userImage);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
