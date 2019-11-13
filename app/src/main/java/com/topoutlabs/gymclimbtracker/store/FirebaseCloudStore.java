package com.topoutlabs.gymclimbtracker.store;

import android.content.Context;

import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.util.Callback;
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

    public FirebaseCloudStore() {

       db = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void saveRoute(Route route) {
        db.child("routes").child(route.getId()).setValue(route);
    }

    @Override
    public void googleLogin(final User user) {
//        System.out.println("Made it to Google login method");
//        System.out.println("Aubry: user.getUid() is" + user.getUid());
        db.child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() { //.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {}
                else{
               //     System.out.println("Made it to here in Google login method");

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
    public void lookUpRoutes(final Gym gym, final Callback<ArrayList<Route>> callback) {
        final ArrayList<Route> routes = new ArrayList<>();
        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {
                    {
                        Route route = routeSnapshot.getValue(Route.class);
                        if(routeSnapshot.exists()) {
                            if (gym != null) {
                                if (route.getGymId().equals(gym.getId())) {
                                    System.out.println("Route is: " + route);
                                    routes.add(route);
                                }
                            } else {
                                routes.add(route);
                            }
                        }
                    }
                }
                callback.receive(routes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void saveClimb(final Climb climb, final User user) {

        final ArrayList<String> savedClimbRouteIds = new ArrayList<>();

        lookupClimbsForUser(user, new Callback<ArrayList<Climb>>() {
            @Override
            public void receive(ArrayList<Climb> strings) {
                if(!strings.isEmpty()) {
                    for (Climb c : strings) {
                        savedClimbRouteIds.add(c.getRouteId());
                    }
                }

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

                    if(climb.getUserId() != null && climb.getUserId().equals(user.getUid())) {
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
    public void updateNumAttempts(Climb climb, int numAttempts) {
        db.child("climbs").child(climb.getId()).child("numAttempts").setValue(numAttempts);
    }

    @Override
    public void updateRouteNotes(Climb climb, String routeNotes) {
        db.child("climbs").child(climb.getId()).child("routeNotes").setValue(routeNotes);
    }

    @Override
    public void updateSent(Climb climb, boolean sent) {
        db.child("climbs").child(climb.getId()).child("sent").setValue(sent);
    }

    @Override
    public void lookupGyms(final Callback<ArrayList<Gym>> callback) {
        final ArrayList<Gym> gyms = new ArrayList<>();

        db.child("gyms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot gymSnapshot : snapshot.getChildren()) {

                    Gym gym = gymSnapshot.getValue(Gym.class);

                    gyms.add(gym);
                }
                callback.receive(gyms);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void saveGym(Gym gym) {
        db.child("gyms").child(gym.getId()).setValue(gym);
    }
}
