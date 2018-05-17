package com.awverret.gymclimbtracker.store;

import android.content.Context;

import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.model.UserRouteInfo;
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
import java.util.List;

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

    //Need to rework this method to take different users into account.

    @Override
    public void saveClimb(final Climb climb, final User user) {

        final ArrayList<String> savedClimbRouteIds = new ArrayList<>();

        lookupClimbsForUser(user, new Callback<ArrayList<Climb>>() {
            @Override
            public void receive(ArrayList<Climb> strings) {
                for(Climb c : strings){
                    System.out.println("VERRET: ClimbId " + c.getRouteId());
                    savedClimbRouteIds.add(c.getRouteId());
                }
                System.out.println("VERRET: SavedClimbRouteIds" + savedClimbRouteIds);

                if(!savedClimbRouteIds.contains(climb.getRouteId())) {
                    db.child("climbs").child(climb.getId()).setValue(climb);

                    db.child("userToClimbsIndex").child(climb.getUserId()).runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {

                            List<String> climbs =
                                    mutableData.getValue(new GenericTypeIndicator<List<String>>() {
                                    });

                            if (climbs == null) {
                                climbs = new ArrayList<>();
                            }

                            if (!climbs.contains(climb.getId())) {
                                climbs.add(climb.getId());
                                mutableData.setValue(climbs);
                                return Transaction.success(mutableData);
                            }

                            return Transaction.abort();
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            System.out.println("databaseError = " + databaseError);
                            System.out.println("Complete = " + dataSnapshot);
                        }
                    });
                }
            }
        });

       }

    @Override
    public void lookupClimbsForUser(final User user, final Callback<ArrayList<Climb>> callback) {
        final ArrayList<Climb> climbs = new ArrayList<>();

        db.child("climbs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot climbSnapshot : snapshot.getChildren()) {

                    Climb climb = climbSnapshot.getValue(Climb.class);

                    if(climb.getUserId().equals(user.getUid())) {
                        climbs.add(climb);
                    }
                }
                callback.receive(climbs);
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void lookupRouteName(final String uid, final Callback<String> callback){

        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            String routeName = null;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {

                    if(routeSnapshot.getKey().equals(uid)) {
                        Route route = routeSnapshot.getValue(Route.class);
                        routeName = route.getName();
                    }
                }

                callback.receive(routeName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void lookupRouteFromClimb(final Climb climb, final Callback<Route> callback) {

        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            Route route = new Route();

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {

                    if(routeSnapshot.getKey().equals(climb.getRouteId())) {
                        route = routeSnapshot.getValue(Route.class);
                        break;
                    }
                }

                callback.receive(route);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void saveUserRouteInfo(UserRouteInfo info) {
        db.child("userRouteInfo").child(info.getId()).setValue(info);
    }

    @Override
    public void lookupUserRouteInfo(Callback<UserRouteInfo> callback) {

    }

}
