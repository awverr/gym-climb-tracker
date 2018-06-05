package com.awverret.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 10/3/17.
 */

public class Climb implements Parcelable {

    public String id;
    public String userId;
    public String routeId;
    //need to add send date

    public int numAttempts;
    public String routeNotes;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Climb createFromParcel(Parcel in) {
            return new Climb(in);
        }

        public Climb[] newArray(int size) {
            return new Climb[size];
        }
    };

    public Climb(){

    }

    public Climb(String userId, String routeId){
        id = randomUUID().toString();
        this.userId = userId;
        this.routeId = routeId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public String getRouteNotes() {
        return routeNotes;
    }

    public Climb(Parcel in){
        this.id = in.readString();
        this.userId = in.readString();
        this.routeId = in.readString();
        this.numAttempts = in.readInt();
        this.routeNotes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.routeId);
        dest.writeInt(this.numAttempts);
        dest.writeString(this.routeNotes);
    }
}
