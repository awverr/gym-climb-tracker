package com.topoutlabs.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Gym implements Parcelable {

    public String id;
    public String name;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Gym createFromParcel(Parcel in) {
            return new Gym(in);
        }

        public Gym[] newArray(int size) {
            return new Gym[size];
        }
    };

    public Gym(){

    }



    public Gym(Parcel in){
//        this.id = in.readString();
//        this.userId = in.readString();
//        this.routeId = in.readString();
//        this.numAttempts = in.readInt();
//        this.routeNotes = in.readString();
//        this.sent = in.readInt() == 1 ? true : false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
