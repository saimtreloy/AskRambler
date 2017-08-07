package com.synergyinterface.askrambler.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
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
    }


    public void SaveGetAllPost() {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String from_where = jsonObjectList.getString("from_where");
                                    String to_where = jsonObjectList.getString("to_where");
                                    String from_date = jsonObjectList.getString("from_date");
                                    String to_date = jsonObjectList.getString("to_date");
                                    String payment_category = jsonObjectList.getString("payment_category");
                                    String gender = jsonObjectList.getString("gender");
                                    String traveling_by = jsonObjectList.getString("traveling_by");
                                    String isType = jsonObjectList.getString("isType");
                                    String baggage_type = jsonObjectList.getString("baggage_type");
                                    String baggage_weight = jsonObjectList.getString("baggage_weight");
                                    String trip_category = jsonObjectList.getString("trip_category");
                                    String trip_category_id = jsonObjectList.getString("trip_category_id");
                                    String transport_type = jsonObjectList.getString("transport_type");
                                    String trip_duration = jsonObjectList.getString("trip_duration");
                                    String location = jsonObjectList.getString("location");
                                    String travelers = jsonObjectList.getString("travelers");
                                    String offers_1 = jsonObjectList.getString("offers_1");
                                    String offers_2 = jsonObjectList.getString("offers_2");
                                    String offers_3 = jsonObjectList.getString("offers_3");
                                    String smoking_habit = jsonObjectList.getString("smoking_habit");
                                    String alcohol_habit = jsonObjectList.getString("alcohol_habit");
                                    String images = jsonObjectList.getString("images");
                                    String contacts = jsonObjectList.getString("contacts");
                                    String details = jsonObjectList.getString("details");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String ad_type_id = jsonObjectList.getString("ad_type_id");
                                    String date = jsonObjectList.getString("date");
                                    String status = jsonObjectList.getString("status");
                                    String user_id = jsonObjectList.getString("user_id");
                                    String user_photo = jsonObjectList.getString("user_photo");
                                    String vote_count = jsonObjectList.getString("vote_count");
                                    ModelPost modelPost = new ModelPost(from_where, to_where, from_date, to_date, payment_category, gender, traveling_by,
                                            isType, baggage_type, baggage_weight, trip_category, trip_category_id, transport_type, trip_duration, location,
                                            travelers, offers_1, offers_2, offers_3, smoking_habit, alcohol_habit, images, contacts, details, ad_type,
                                            ad_type_id, date, status, user_id, user_photo, vote_count);
                                    modelPostsList.add(modelPost);
                                }

                            }
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(Splash.this).addToRequestQueue(stringRequest);

    }
}
