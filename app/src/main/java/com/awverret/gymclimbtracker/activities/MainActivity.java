package com.awverret.gymclimbtracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.awverret.gymclimbtracker.store.LocalStore;
import com.awverret.gymclimbtracker.store.PreferencesLocalStore;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.common.base.Optional;
import com.google.firebase.FirebaseApp;

import java.util.List;

public class MainActivity extends AppCompatActivity{

        CloudStore store;

    LocalStore localStore = new PreferencesLocalStore(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        store = new FirebaseCloudStore(this);

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

    public void displayRoutes(MainActivity view){
       List<String> routes =  store.lookUpRoutes(new Callback<List<String>>() {
           @Override
           public void receive(List<String> strings) {

           }
       });

        System.out.println("VERRET: Routes are: " + routes);
    }

    public void clickAddRoute(View view){
        startActivity(new Intent(MainActivity.this, AddRouteActivity.class));
    }

    public void logout(View view){
        store.googleLogout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
