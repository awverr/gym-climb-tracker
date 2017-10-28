package com.awverret.gymclimbtracker.store;

import android.content.Context;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.model.UserRouteHistory;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aubry on 3/1/17.
 */

public class FirebaseCloudStore implements CloudStore {

 private DatabaseReference db;

    public FirebaseCloudStore(Context context) {

       db = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void saveRoute(Route route) {
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
                System.out.println("VERRET: Cloustore login success");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("VERRET: Cloustore login cancelled");
            }

        });
    }

    @Override
    public void googleLogout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void lookUpRoutes(final Callback<ArrayList<Route>> callback) {
        final ArrayList<Route> routes = new ArrayList<>();

        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {

                    Route route = routeSnapshot.getValue(Route.class);
                    routes.add(route);
                }
                callback.receive(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void saveClimb(final Climb climb) {
        db.child("climbs").child(climb.getId()).setValue(climb);

       db.child("userToClimbsIndex").child(climb.getUserId()).runTransaction(new Transaction.Handler() {
           @Override
           public Transaction.Result doTransaction(MutableData mutableData) {
               return null;
           }

           @Override
           public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

           }
       });

       }

    @Override
    public void lookupClimbs(final Callback<ArrayList<Climb>> callback) {
        final ArrayList<Climb> climbs = new ArrayList<>();

        db.child("climbs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot climbSnapshot : snapshot.getChildren()) {

                    Climb climb = climbSnapshot.getValue(Climb.class);
                    climbs.add(climb);
                }
                callback.receive(climbs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}
