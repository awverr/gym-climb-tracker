package com.awverret.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 4/9/18.
 */

public class UserRouteInfo implements Parcelable{

    public String id;
    public String userId;
    public String routeId;

    int numAttempts;
    String routeNotes;
    boolean sent = false;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserRouteInfo createFromParcel(Parcel in) {
            return new UserRouteInfo(in);
        }

        public UserRouteInfo[] newArray(int size) {
            return new UserRouteInfo[size];
        }
    };

    public UserRouteInfo(){

    }

    public UserRouteInfo(String userId, String routeId){
        id = randomUUID().toString();
        this.userId = userId;
        this.routeId = routeId;
    }

    public String getId() {
        return id;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public void incrementNumAttempts() {
        numAttempts++;
    }

    public String getRouteNotes() {
        return routeNotes;
    }

    public void setRouteNotes(String routeNotes) {
        this.routeNotes = routeNotes;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }

    public boolean getSent() {
        return sent;
    }

    public UserRouteInfo(Parcel in){
        this.id = in.readString();
        this.userId = in.readString();
        this.routeId = in.readString();
        this.numAttempts = in.readInt();
        this.routeNotes = in.readString();
        this.sent = in.readInt() == 1 ? true : false;
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
        dest.writeInt(sent ? 1 : 0);
    }

}
