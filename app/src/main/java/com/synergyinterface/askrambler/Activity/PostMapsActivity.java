package com.synergyinterface.askrambler.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.synergyinterface.askrambler.Model.ModelPostShortMap;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public ArrayList<ModelPostShortMap> modelPostShortMaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_maps);

        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void init(){
        for (int i=0; i<Splash.modelPostsList.size(); i++){
            getAllLatLon(Splash.modelPostsList.get(i).getTo_where());
        }
    }

    public void getAllLatLon(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getLatLongFromLocation+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                                JSONObject jsonObjectGeometry = jsonObjectResult.getJSONObject("geometry");
                                JSONObject jsonObjectLocation = jsonObjectGeometry.getJSONObject("location");
                                Double lat = jsonObjectLocation.getDouble("lat");
                                Double lng = jsonObjectLocation.getDouble("lng");
                                Log.d("SAIM SPLASH LATLNG", lat + " : " + lng );

                                ModelPostShortMap m = new ModelPostShortMap(Splash.modelPostsList.get(i).getAds_id(),
                                        Splash.modelPostsList.get(i).getTo_where(),
                                        Splash.modelPostsList.get(i).getTo_date(),
                                        Splash.modelPostsList.get(i).getAd_type(),
                                        Splash.modelPostsList.get(i).getDetails(),
                                        Splash.modelPostsList.get(i).getFull_name(),
                                        Splash.modelPostsList.get(i).getUser_photo(),
                                        lat+"",
                                        lng+"");
                                modelPostShortMaps.add(m);
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
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for (int i=0; i<modelPostShortMaps.size(); i++){
            LatLng sydney = new LatLng(Double.parseDouble(modelPostShortMaps.get(i).getLat()), Double.parseDouble(modelPostShortMaps.get(i).getLon()) );
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        }
        Double myLat = Double.parseDouble(new SharedPrefDatabase(getApplicationContext()).RetriveLat());
        Double myLon = Double.parseDouble(new SharedPrefDatabase(getApplicationContext()).RetriveLon());
        LatLng myLocation = new LatLng(myLat, myLon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}
