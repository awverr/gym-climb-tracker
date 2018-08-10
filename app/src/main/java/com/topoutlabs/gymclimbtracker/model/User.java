package com.topoutlabs.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubry on 3/22/2017.
 */

public class User implements Parcelable {

    private String uid;
    private String emailAddress;
    private String firstName;
    private String lastName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String emailAddress, String firstName, String lastName){
        this.uid = uid;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User(Parcel in){
        this.uid = in.readString();
        this.emailAddress = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.emailAddress);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }
}
