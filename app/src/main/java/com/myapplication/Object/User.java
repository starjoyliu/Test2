package com.myapplication.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by star on 2017/11/3.
 */

public class User implements Parcelable {
    private String name;
    private String phone;
    private String bir;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public User(String name, String phone, String bir) {

        this.name = name;
        this.phone = phone;
        this.bir = bir;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.bir);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.bir = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
