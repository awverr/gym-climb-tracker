package com.topoutlabs.gymclimbtracker.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.fragments.AddGymFragment;
import com.topoutlabs.gymclimbtracker.fragments.AddRouteFragment;
import com.topoutlabs.gymclimbtracker.fragments.ChooseGymFragment;
import com.topoutlabs.gymclimbtracker.fragments.ViewAllRoutesFragment;
import com.topoutlabs.gymclimbtracker.fragments.ViewHistoryFragment;
import com.topoutlabs.gymclimbtracker.fragments.ViewRouteFragment;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.store.PreferencesLocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Optional;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CloudStore store;
    LocalStore localStore = new PreferencesLocalStore(this);

    DrawerLayout drawer;
    NavigationView navigationView;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_name);

        store = new FirebaseCloudStore();

        if (findViewById(R.id.fragment_container) != null) {
                // Create a new Fragment to be placed in the activity layout
                ChooseGymFragment chooseGymFragment = new ChooseGymFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                chooseGymFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chooseGymFragment).commit();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                            // Handle navigation view item clicks here.
                            int id = item.getItemId();

                            if (id == R.id.view_history) {

                                if (drawer != null) {
                                    item.setChecked(true);
                                    drawer.closeDrawer(navigationView);
                                }

                                // Create a new Fragment to be placed in the activity layout
                                ViewHistoryFragment viewHistoryFragment = new ViewHistoryFragment();

                                Bundle bundle = new Bundle();
                                bundle.putParcelable("user", localStore.getUser().get());

                                viewHistoryFragment.setArguments(bundle);

                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                // Add the fragment to the 'fragment_container' FrameLayout
                                transaction.replace(R.id.fragment_container, viewHistoryFragment).commit();
                                transaction.addToBackStack(null);

                            } else if (id == R.id.route_list) {

                                if (drawer != null) {
                                    item.setChecked(true);
                                    drawer.closeDrawer(navigationView);
                                }

                                // Create a new Fragment to be placed in the activity layout
                                ViewAllRoutesFragment viewAllRoutesFragment = new ViewAllRoutesFragment();

                                viewAllRoutesFragment.setArguments(getIntent().getExtras());

                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                // Add the fragment to the 'fragment_container' FrameLayout
                                transaction.replace(R.id.fragment_container, viewAllRoutesFragment).commit();
                                transaction.addToBackStack(null);

                            }else if (id == R.id.add_route) {

                                if (drawer != null) {
                                    item.setChecked(true);
                                    drawer.closeDrawer(navigationView);
                                }

                                AddRouteFragment addRouteFragment = new AddRouteFragment();

                                addRouteFragment.setArguments(getIntent().getExtras());

                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                transaction.replace(R.id.fragment_container, addRouteFragment).commit();
                                transaction.addToBackStack(null);

                            } else if (id == R.id.log_out) {
                                if (drawer != null) {
                                    item.setChecked(true);
                                    drawer.closeDrawer(navigationView);
                                }

                                store.googleLogout();
                                localStore.clearUser();
                                signOut();

                                Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                                startActivity(intent);
                            }

                            return true;
                        }
                });
        //Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        load();
    }

    private void load() {
        Optional<User> user = localStore.getUser();

        if (!user.isPresent()) {
            // send to login if not logged in
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "You are completely signed out", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public LocalStore getLocalStore() {
        return localStore;
    }
}
