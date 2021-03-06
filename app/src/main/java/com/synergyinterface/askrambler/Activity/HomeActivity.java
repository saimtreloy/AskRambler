package com.synergyinterface.askrambler.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.synergyinterface.askrambler.Fragment.FragmentAddPost;
import com.synergyinterface.askrambler.Fragment.FragmentAdvancedSearch;
import com.synergyinterface.askrambler.Fragment.FragmentAllPost;
import com.synergyinterface.askrambler.Fragment.FragmentLogin;
import com.synergyinterface.askrambler.Fragment.FragmentProfile;
import com.synergyinterface.askrambler.Model.ModelPostShort;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Service.MyService;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.CircleTransform;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;
    public static android.support.v4.app.FragmentTransaction fragmentTransaction;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*if (havePermission()){
            startService(new Intent(getApplicationContext(), MyService.class));
        }*/

        Initialization();
    }


    public void Initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);

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

        View headerView = navigationView.getHeaderView(0);

        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.btnMenuSignIn).setVisible(false);

        if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin() == null){
            PopulateViewOnNotLogin();
        }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().toString().equals("Yes") ){
            PopulateViewOnLogin();
        }else {
            PopulateViewOnNotLogin();
        }

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

    public void PopulateViewOnLogin(){
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.btnMenuSignIn).setVisible(false);
        nav_menu.findItem(R.id.btnMenuProfile).setVisible(true);

        View headerView = navigationView.getHeaderView(0);
        ImageView imageViewHeader = (ImageView) headerView.findViewById(R.id.imageViewHeader);
        ImageView imgLogoutHeader = (ImageView) headerView.findViewById(R.id.imgLogoutHeader);
        TextView txtNameHeader = (TextView) headerView.findViewById(R.id.txtNameHeader);
        TextView txtRatingHeader = (TextView) headerView.findViewById(R.id.txtRatingHeader);

        Log.d("SAIM INFO CHECK", new SharedPrefDatabase(getApplicationContext()).RetriveUserFullName() + "\n" +
        new SharedPrefDatabase(getApplicationContext()).RetriveUserPhoto());

        if (Splash.user_photo.equals("http://askrambler.com/")){
            imageViewHeader.setImageResource(R.drawable.ic_person);
        }else {
            Glide.with(getApplicationContext())
                    .load(new SharedPrefDatabase(getApplicationContext()).RetriveUserPhoto()).transform(new CircleTransform(getApplicationContext()))
                    .placeholder(R.drawable.ic_person)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageViewHeader);
        }


        txtNameHeader.setText(new SharedPrefDatabase(getApplicationContext()).RetriveUserFullName());

        imgLogoutHeader.setVisibility(View.VISIBLE);
        imgLogoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AwesomeWarningDialog(v.getContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure want to logout?")
                        .setColoredCircle(R.color.dialogWarningBackgroundColor)
                        .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                        .setCancelable(true)
                        .setButtonText(getString(R.string.dialog_ok_button))
                        .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
                        .setButtonText(getString(R.string.dialog_ok_button))
                        .setWarningButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                new SharedPrefDatabase(getApplicationContext()).StoreUserEmail("");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserPassword("");
                                new SharedPrefDatabase(getApplicationContext()).StoreLogin("No");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserFullName("Guest User");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserPhoto("");
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        })
                        .show();
            }
        });
    }

    public void PopulateViewOnNotLogin(){
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.btnMenuProfile).setVisible(false);
        nav_menu.findItem(R.id.btnMenuSignIn).setVisible(true);

        View headerView = navigationView.getHeaderView(0);
        ImageView imageViewHeader = (ImageView) headerView.findViewById(R.id.imageViewHeader);
        ImageView imgLogoutHeader = (ImageView) headerView.findViewById(R.id.imgLogoutHeader);
        TextView txtNameHeader = (TextView) headerView.findViewById(R.id.txtNameHeader);
        TextView txtRatingHeader = (TextView) headerView.findViewById(R.id.txtRatingHeader);

        imageViewHeader.setImageResource(R.drawable.ic_person);
        imgLogoutHeader.setVisibility(View.GONE);
        txtNameHeader.setText("Guest User");
        txtRatingHeader.setText("5.00");
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

    public BroadcastReceiver receiverChangeLayoutOnLogin = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin() == null){
                PopulateViewOnNotLogin();
            }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().toString().equals("Yes") ){
                PopulateViewOnLogin();
            }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().toString().equals("No")){
                PopulateViewOnNotLogin();
            }
        }
    };

    public BroadcastReceiver receiverPost = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog.setMessage("Please wait..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            SaveGetAllPost();
        }
    };

    public void SaveGetAllPost() {
        Splash.modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPostShort,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String ads_id = jsonObjectList.getString("add_id");
                                    String to_where = jsonObjectList.getString("to_where");
                                    Log.d("Saim Khan", to_where);
                                    String to_date = jsonObjectList.getString("to_date");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String details = jsonObjectList.getString("details");
                                    String full_name = jsonObjectList.getString("full_name");
                                    String user_photo = jsonObjectList.getString("user_photo");

                                    ModelPostShort modelPostShort = new ModelPostShort(ads_id, to_where, to_date,ad_type, details, full_name, user_photo);
                                    Splash.modelPostsList.add(modelPostShort);
                                }

                            }else {
                                Log.d("SAIM SPLASH 3", response);
                            }

                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new FragmentAllPost());
                            fragmentTransaction.commit();

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.synergyinterface.askrambler.Activity.ChangeLayoutOnLogin");
        registerReceiver(receiverChangeLayoutOnLogin, intentFilter);

        IntentFilter intentFilter2 = new IntentFilter("com.synergyinterface.askrambler.Activity.receiverPost");
        registerReceiver(receiverPost, intentFilter2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverChangeLayoutOnLogin);
        unregisterReceiver(receiverPost);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnMenuLocation:
                startActivity(new Intent(this, PostMapsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean havePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }
}
