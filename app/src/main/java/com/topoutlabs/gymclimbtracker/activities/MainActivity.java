package com.topoutlabs.gymclimbtracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.awverret.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.store.PreferencesLocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;
import com.google.common.base.Optional;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import static com.awverret.gymclimbtracker.R.id.routes_list_view;

public class MainActivity extends AppCompatActivity{

    CloudStore store;

    LocalStore localStore = new PreferencesLocalStore(this);

    Spinner routeSpinner;

    ListView routesView;

    ArrayList<String> routes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        store = new FirebaseCloudStore(this);

        routeSpinner = (Spinner) findViewById(R.id.route_spinner);

        routesView = (ListView) findViewById(routes_list_view);

        load();

        displayRoutes(this);
    }

    private void load() {
        Optional<User> user = localStore.getUser();

        if (!user.isPresent()) {
            // send to login if not logged in
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void initializeRouteSpinner(Spinner spinner){

        ArrayAdapter<String> routeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routes);
        routeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeSpinner.setAdapter(routeSpinnerAdapter);
    }

    public void initializeListView(ListView lv){

        ArrayAdapter<String> routesViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, routes);
        routesView.setAdapter(routesViewAdapter);
    }

    public void displayRoutes(MainActivity view){

       store.lookUpRoutes(new Callback<ArrayList<String>>() {
           @Override
           public void receive(ArrayList<String> strings) {

               routes = strings;
               initializeRouteSpinner(routeSpinner);
               initializeListView(routesView);
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
