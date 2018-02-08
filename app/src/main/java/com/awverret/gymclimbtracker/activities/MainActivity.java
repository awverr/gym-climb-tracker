package com.awverret.gymclimbtracker.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.awverret.gymclimbtracker.store.LocalStore;
import com.awverret.gymclimbtracker.store.PreferencesLocalStore;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.common.base.Optional;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

import static com.awverret.gymclimbtracker.R.id.activity_main;

import static com.awverret.gymclimbtracker.util.Utils.createRouteName;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CloudStore store;

    LocalStore localStore = new PreferencesLocalStore(this);

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter recyclerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

//    ArrayList<String> routes = new ArrayList<>();
    ArrayList<Route> routeList = new ArrayList<>(); //For use in recylcer view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        store = new FirebaseCloudStore(this);

        initializeRecyclerView(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.setDrawerListener(new DrawerLayout.DrawerListener(this, drawer, toolbar));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            //    getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
         //       getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawer.setDrawerListener(mDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        load();
    }

    private void initializeRecyclerView(final MainActivity view) {
        store.lookUpRoutes(new Callback<ArrayList<Route>>() {
            @Override
            public void receive(ArrayList<Route> strings) {

                for(Route r : strings){
        //            routes.add(r.getName());
                    routeList.add(r);
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerAdapter = new RouteRecyclerAdapter(routeList, localStore.getUser().get(), MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    private void load() {
        Optional<User> user = localStore.getUser();

        if (!user.isPresent()) {
            // send to login if not logged in
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void clickAddRoute(View view){
        startActivity(new Intent(MainActivity.this, AddRouteActivity.class));
    }

    public void logout(View view){
        store.googleLogout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void clickViewHistory(View view){
        startActivity(new Intent(MainActivity.this, ViewClimbsActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_history) {
            Intent intent = new Intent(this, ViewClimbsActivity.class);

            startActivity(intent);

        } else if (id == R.id.add_route) {
            Intent intent = new Intent(this, AddRouteActivity.class);

            startActivity(intent);

        } else if (id == R.id.log_out) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        }

       return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_drawer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
