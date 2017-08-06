package com.synergyinterface.askrambler.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.synergyinterface.askrambler.MainActivity;
import com.synergyinterface.askrambler.Model.ModelPost;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    public static ArrayList<ModelPost> modelPostsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SaveGetAllPost();

        /*Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };
        timer.start();*/
    }


    public void SaveGetAllPost() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new SharedPrefDatabase(getApplicationContext()).StoreGetAllPost(response);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(Splash.this).addToRequestQueue(stringRequest);

    }
}
