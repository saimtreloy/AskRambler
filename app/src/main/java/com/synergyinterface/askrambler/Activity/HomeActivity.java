package com.synergyinterface.askrambler.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.synergyinterface.askrambler.Fragment.FragmentAddPost;
import com.synergyinterface.askrambler.Fragment.FragmentAdvancedSearch;
import com.synergyinterface.askrambler.Fragment.FragmentAllPost;
import com.synergyinterface.askrambler.Fragment.FragmentLogin;
import com.synergyinterface.askrambler.Fragment.FragmentProfile;
import com.synergyinterface.askrambler.R;

public class HomeActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Initialization();
    }


    public void Initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new FragmentAllPost());
        fragmentTransaction.commit();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Toast.makeText(getApplicationContext(), "Drawer Open", Toast.LENGTH_LONG).show();
        }

        NavigationItemClicked();
    }


    public void NavigationItemClicked() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btnMenuSignIn) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new FragmentLogin());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                } else if (item.getItemId() == R.id.btnMenuProfile) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new FragmentProfile());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                } else if (item.getItemId() == R.id.btnMenuAddPost) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new FragmentAddPost());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                } else if (item.getItemId() == R.id.btnMenuAdvancedSearch) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new FragmentAdvancedSearch());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            toolbar.setTitle(getResources().getString(R.string.app_name));
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}
