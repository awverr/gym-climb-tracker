package com.awverret.gymclimbtracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity{

    CloudStore store;

    LocalStore localStore = new PreferencesLocalStore(this);

//    Spinner routeSpinner;

    ListView routesView;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RouteRecyclerAdapter recyclerAdapter;

    ArrayList<String> routes = new ArrayList<>();
    ArrayList<Route> routeList = new ArrayList<>(); //For use in recylcer view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        store = new FirebaseCloudStore(this);

 //       routeSpinner = (Spinner) findViewById(R.id.route_spinner);

  //      routesView = (ListView) findViewById(routes_list_view);

        initializeRecyclerView(this);



//        mLinearLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        load();

   //     displayRoutes(this);
    }

    private void initializeRecyclerView(MainActivity view) {
        store.lookUpRoutes(new Callback<ArrayList<Route>>() {
            @Override
            public void receive(ArrayList<Route> strings) {

                for(Route r : strings){
                    routes.add(createRouteName(r));
                    routeList.add(r);
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerAdapter = new RouteRecyclerAdapter(routeList);
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

//    public void initializeRouteSpinner(Spinner spinner){
//
//        ArrayAdapter<String> routeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routes);
//        routeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        routeSpinner.setAdapter(routeSpinnerAdapter);
//    }

    public void initializeListView(ListView lv){

        ArrayAdapter<String> routesViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, routes);
        routesView.setAdapter(routesViewAdapter);
    }

    public void displayRoutes(MainActivity view){

       store.lookUpRoutes(new Callback<ArrayList<Route>>() {
           @Override
           public void receive(ArrayList<Route> strings) {

               for(Route r : strings){
                   routes.add(createRouteName(r));
                   routeList.add(r);
               }
               System.out.println("VERRET: routelist: " + routeList);
            //   initializeRouteSpinner(routeSpinner);
         //      initializeListView(routesView);
               System.out.println("VERRET: Routes are: " + strings);
           }
       });


    }

    public void clickAddRoute(View view){
        startActivity(new Intent(MainActivity.this, AddRouteActivity.class));
    }

    public void logout(View view){
        store.googleLogout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
