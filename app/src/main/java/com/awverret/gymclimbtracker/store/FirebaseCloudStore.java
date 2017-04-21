package com.awverret.gymclimbtracker.store;

import android.content.Context;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aubry on 3/1/17.
 */

public class FirebaseCloudStore implements CloudStore {

 private DatabaseReference db;

    public FirebaseCloudStore(Context context) {

       db = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public void saveRoute(LeadRoute route) {
        db.child("routes").child(route.getId()).setValue(route);
    }

    @Override
    public void saveRoute(TopRopeRoute route) {
        db.child("routes").child(route.getId()).setValue(route);
    }

    @Override
    public void saveRoute(BoulderRoute route) {
        db.child("routes").child(route.getId()).setValue(route);
    }

    @Override
    public void googleLogin(User user, Callback<User> callback) {

    }
}
