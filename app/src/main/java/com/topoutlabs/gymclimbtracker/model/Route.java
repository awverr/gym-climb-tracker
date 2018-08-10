package com.topoutlabs.gymclimbtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import static com.topoutlabs.gymclimbtracker.util.Utils.createRouteName;
import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 2/24/17.
 */

public class Route implements Parcelable{

    public RouteType type;
    public String id;
    public RouteGrade routeGrade;
    public RouteColor color;
    public RouteWall wall;
    public String name;
    public String setter;
    //Date setDate;
    public long setDate;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public Route() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Route(RouteType type, RouteGrade routeGrade, String name, String setter, RouteColor color, RouteWall wall, long setDate) {
        this.type = type;
        id = randomUUID().toString();
        this.routeGrade = routeGrade;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;

        if(name == null) {
            this.name = createRouteName();
        }else {
            this.name = name;
        }
    }

    public String createRouteName(){

        return wall.getText() + "/ " + color.getText() + "/ " + routeGrade.getText();
    }

    public RouteType getType() {return type;}

    public String getId() {return id;}

    public RouteGrade getRouteGrade() {
        return routeGrade;
    }

    public String getSetter() {
        return setter;
    }

    public String getName() {
        return name;
    }

    public RouteColor getColor() {
        return color;
    }

    public RouteWall getWall() {
        return wall;
    }

    public long getSetDate() {return setDate;}

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                '}';
    }

    public Route(Parcel in){
        this.type = (RouteType) in.readSerializable();
        this.id = in.readString();
        this.routeGrade = (RouteGrade) in.readSerializable();
        this.color = (RouteColor) in.readSerializable();
        this.wall = (RouteWall) in.readSerializable();
        this.name = in.readString();
        this.setter = (String) in.readSerializable();
        this.setDate = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeSerializable(this.type);
        dest.writeString(this.id);
        dest.writeSerializable(this.routeGrade);
        dest.writeSerializable(this.color);
        dest.writeSerializable(this.wall);
        dest.writeString(this.name);
        dest.writeSerializable(this.setter);
        dest.writeLong(this.setDate);
    }
}
