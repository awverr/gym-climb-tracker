package com.awverret.gymclimbtracker.store;

import android.content.Context;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void googleLogin(final User user) {

        db.child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() { //.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {}
                else{
                    db.child("users").child(user.getUid()).setValue(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void googleLogout() {
        FirebaseAuth.getInstance().signOut();
    }
}
