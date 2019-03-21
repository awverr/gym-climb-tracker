package com.topoutlabs.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import static java.util.UUID.randomUUID;

public class Gym implements Parcelable {

    public String id;
    public String name;
    public String state;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Gym createFromParcel(Parcel in) {
            return new Gym(in);
        }

        public Gym[] newArray(int size) {
            return new Gym[size];
        }
    };

    public Gym(){
        //constructor required for calls to DataSnapshot.getValue(Gym.class)
    }

    public Gym(String name, String state){
        id = randomUUID().toString();
        this.name = name;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Gym(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.state = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.state);
    }
}
