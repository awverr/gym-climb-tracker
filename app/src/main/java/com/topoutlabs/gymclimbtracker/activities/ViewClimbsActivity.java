package com.topoutlabs.gymclimbtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

public class ViewClimbsActivity extends AppCompatActivity {

    CloudStore store;

    User user; //Need to initialize!!

    private RecyclerView mRecyclerView;
    private ClimbRecyclerAdapter recyclerAdapter;

    ArrayList<Climb> climbList = new ArrayList<>(); //For use in recycler view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_climbs);

        store = new FirebaseCloudStore();

        initializeRecyclerView(this);
    }

    private void initializeRecyclerView(final ViewClimbsActivity view) {
        store.lookupClimbsForUser(user, new Callback<ArrayList<Climb>>() {
            @Override
            public void receive(ArrayList<Climb> strings) {

                for(Climb c : strings){
                    climbList.add(c);
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.climb_recycler_view);
                recyclerAdapter = new ClimbRecyclerAdapter(ViewClimbsActivity.this, climbList, user);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}
