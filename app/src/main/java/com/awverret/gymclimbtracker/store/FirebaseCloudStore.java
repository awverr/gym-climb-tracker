package com.awverret.gymclimbtracker.store;

import android.content.Context;

import com.awverret.gymclimbtracker.activities.MainActivity;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.LeadClimb;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aubry on 3/1/17.
 */

public class FirebaseCloudStore implements CloudStore {

 private DatabaseReference db;

    public FirebaseCloudStore(Context context) {

       db = FirebaseDatabase.getInstance().getReference();

       // FirebaseDatabase database = FirebaseDatabase.getInstance();



    }

    @Override
    public void saveLeadClimb(LeadClimb climb) {
     db.child("climbs").child(climb.getId()).setValue(climb);
    }
}
