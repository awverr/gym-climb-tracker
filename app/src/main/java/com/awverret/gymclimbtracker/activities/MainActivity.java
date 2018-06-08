package com.awverret.gymclimbtracker.activities;

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

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.fragments.AddRouteFragment;
import com.awverret.gymclimbtracker.fragments.ViewAllRoutesFragment;
import com.awverret.gymclimbtracker.fragments.ViewHistoryFragment;
import com.awverret.gymclimbtracker.fragments.ViewRouteFragment;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.awverret.gymclimbtracker.store.LocalStore;
import com.awverret.gymclimbtracker.store.PreferencesLocalStore;
import com.awverret.gymclimbtracker.util.Callback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Optional;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CloudStore store;

    LocalStore localStore = new PreferencesLocalStore(this);

    private ActionBarDrawerToggle mDrawerToggle;

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

        store = new FirebaseCloudStore(this);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ViewAllRoutesFragment viewAllRoutesFragment = new ViewAllRoutesFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            viewAllRoutesFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, viewAllRoutesFragment).commit();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_history) {

            if (drawer != null) {
                drawer.closeDrawer(navigationView);
            }

            // Create a new Fragment to be placed in the activity layout
            ViewHistoryFragment viewHistoryFragment = new ViewHistoryFragment();

            Bundle bundle = new Bundle();
            System.out.println("VERRET: User in MainActivity is: " + localStore.getUser().get());
            bundle.putParcelable("user", localStore.getUser().get());

            viewHistoryFragment.setArguments(bundle);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //viewHistoryFragment.setArguments(getIntent().getExtras());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Add the fragment to the 'fragment_container' FrameLayout
            transaction.replace(R.id.fragment_container, viewHistoryFragment).commit();
            transaction.addToBackStack(null);

        } else if (id == R.id.add_route) {

            if (drawer != null) {
                drawer.closeDrawer(navigationView);
            }

            AddRouteFragment addRouteFragment = new AddRouteFragment();

            addRouteFragment.setArguments(getIntent().getExtras());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, addRouteFragment);
            transaction.addToBackStack(null);

            transaction.commit();

        } else if (id == R.id.log_out) {
            if (drawer != null) {
                drawer.closeDrawer(navigationView);
            }

            store.googleLogout();
            localStore.clearUser();
            signOut();

            Toast.makeText(this, "Signed out", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        }

       return true;
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
}
