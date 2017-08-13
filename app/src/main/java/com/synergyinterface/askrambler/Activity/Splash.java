package com.synergyinterface.askrambler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.synergyinterface.askrambler.Model.ModelPostShort;
import com.synergyinterface.askrambler.Model.ModelUser;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Splash extends AppCompatActivity {

    public static ArrayList<ModelPostShort> modelPostsList = new ArrayList<>();
    public static ModelUser modelUser = new ModelUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SaveGetAllPost();
    }


    public void SaveGetAllPost() {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPostShort,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SPLASH 1", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            Log.d("SAIM SPLASH C", code);
                            if (code.equals("success")) {
                                Log.d("SAIM SPLASH 2", response);
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String ads_id = jsonObjectList.getString("add_id");
                                    String to_where = jsonObjectList.getString("to_where");
                                    String to_date = jsonObjectList.getString("to_date");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String details = jsonObjectList.getString("details");
                                    String full_name = jsonObjectList.getString("full_name");
                                    String user_photo = jsonObjectList.getString("user_photo");

                                    ModelPostShort modelPostShort = new ModelPostShort(ads_id, to_where, to_date,ad_type, details, full_name, user_photo);
                                    modelPostsList.add(modelPostShort);
                                }

                                if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin() != null){
                                    if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().equals("Yes")){
                                        SaveUserLogin();
                                    }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().equals("No")){
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    }
                                }else {
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    finish();
                                }


                            }else {
                                Log.d("SAIM SPLASH 3", response);
                            }


                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(Splash.this).addToRequestQueue(stringRequest);

    }


    public void SaveUserLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM LOGIN INFO", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);

                                String user_id = jsonObjectList.getString("user_id");
                                String nationality = jsonObjectList.getString("nationality");
                                String full_name = jsonObjectList.getString("full_name");
                                String email = jsonObjectList.getString("email");
                                String password = jsonObjectList.getString("password");
                                String agreement = jsonObjectList.getString("agreement");
                                String status = jsonObjectList.getString("status");
                                String roll = jsonObjectList.getString("roll");
                                String first_name = jsonObjectList.getString("first_name");
                                String last_name = jsonObjectList.getString("last_name");
                                String gander = jsonObjectList.getString("gander");
                                String address = jsonObjectList.getString("address");
                                String city = jsonObjectList.getString("city");
                                String zip = jsonObjectList.getString("zip");
                                String state = jsonObjectList.getString("state");
                                String country = jsonObjectList.getString("country");
                                String mobile = jsonObjectList.getString("mobile");
                                String phone = jsonObjectList.getString("phone");
                                String birth_date = jsonObjectList.getString("birth_date");
                                String user_photo = jsonObjectList.getString("user_photo");
                                String document = jsonObjectList.getString("document");
                                String verify = jsonObjectList.getString("verify");
                                String website = jsonObjectList.getString("website");
                                String facebook = jsonObjectList.getString("facebook");
                                String instagram = jsonObjectList.getString("instagram");
                                String youtube = jsonObjectList.getString("youtube");
                                String code1 = jsonObjectList.getString("code");
                                String cornjob = jsonObjectList.getString("cornjob");
                                String like_to = jsonObjectList.getString("like_to");
                                String details = jsonObjectList.getString("details");
                                String server_date = jsonObjectList.getString("server_date");

                                new SharedPrefDatabase(getApplicationContext()).StoreLogin("Yes");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserFullName(full_name);
                                new SharedPrefDatabase(getApplicationContext()).StoreUserPhoto(user_photo);

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();

                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD ", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", "saim.treloy@gmail.com");
                params.put("password", "Saim123");

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
