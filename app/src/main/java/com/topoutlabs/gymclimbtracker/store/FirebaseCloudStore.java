package com.topoutlabs.gymclimbtracker.store;

import android.content.Context;

import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.util.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    @Override
    public void lookUpRoutes(final Callback<ArrayList<String>> callback) {
        final ArrayList<String> routes = new ArrayList<>();

        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {
                    String id = (String) routeSnapshot.child("id").getValue();
                    routes.add(id);
                    System.out.println("VERRET: Routes is now: " + routes);
                    System.out.println("VERRET: Route id is: " + id);
//                    Route route = routeSnapshot.getValue(Route.class);
//                    routes.add(route.getClass().toString());
//                    System.out.println("VERRET: Route is: " + route.getClass().toString());
                }
                System.out.println("VERRET: routes to return is: " + routes);
                callback.receive(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
