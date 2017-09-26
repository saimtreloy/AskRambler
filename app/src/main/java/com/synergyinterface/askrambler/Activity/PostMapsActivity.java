package com.synergyinterface.askrambler.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    LocationRequest mLocationRequest;

    public ArrayList<ModelPostShortMap> modelPostShortMaps = new ArrayList<>();
    public static int iii = 0;
    public ProgressDialog progressDialog;
    Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_maps);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        getAllLatLon(Splash.modelPostsList.get(iii).getTo_where());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getAllLatLon(String url) {
        url = Uri.encode(url);
        url = url.replace("'", "%27");
        Log.d("SAIM URL", ApiURL.getLatLongFromLocation + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getLatLongFromLocation + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                                JSONObject jsonObjectGeometry = jsonObjectResult.getJSONObject("geometry");
                                JSONObject jsonObjectLocation = jsonObjectGeometry.getJSONObject("location");
                                Double lat = jsonObjectLocation.getDouble("lat");
                                Double lng = jsonObjectLocation.getDouble("lng");
                                Log.d("SAIM SPLASH LATLNG", Splash.modelPostsList.get(iii).getTo_where());

                                ModelPostShortMap m = new ModelPostShortMap(Splash.modelPostsList.get(iii).getAds_id(),
                                        Splash.modelPostsList.get(iii).getTo_where(),
                                        Splash.modelPostsList.get(iii).getTo_date(),
                                        Splash.modelPostsList.get(iii).getAd_type(),
                                        Splash.modelPostsList.get(iii).getDetails(),
                                        Splash.modelPostsList.get(iii).getFull_name(),
                                        Splash.modelPostsList.get(iii).getUser_photo(),
                                        lat + "",
                                        lng + "");
                                modelPostShortMaps.add(m);
                            }

                        } catch (Exception e) {

                        }
                        iii++;
                        if (Splash.modelPostsList.size() <= iii) {
                            progressDialog.dismiss();
                            Log.d("SAOM LOG LOG", modelPostShortMaps.get(0).getTo_where());
                            init();
                        } else {
                            getAllLatLon(Splash.modelPostsList.get(iii).getTo_where());
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
        for (int i = 0; i < modelPostShortMaps.size(); i++) {
            Log.d("SAIM MARKER", "Heloo Saim Kamon Aso");
        }


        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(10);
                mLocationRequest.setFastestInterval(10);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        for (int i=0; i<modelPostShortMaps.size(); i++){
            LatLng sydney = new LatLng(Double.parseDouble(modelPostShortMaps.get(i).getLat()), Double.parseDouble(modelPostShortMaps.get(i).getLon()) );
            myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title(modelPostShortMaps.get(i).getTo_where()));
            myMarker.setTag(modelPostShortMaps.get(i).getTo_where());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            Log.d("SAIM MARKER", sydney.toString());
        }

        /*Double lat = Double.parseDouble(new SharedPrefDatabase(getApplicationContext()).RetriveLat());
        Double lon = Double.parseDouble(new SharedPrefDatabase(getApplicationContext()).RetriveLon());

        Log.d("SASIMMM LAT LON", lat + lon +"");

        LatLng myLocation = new LatLng(lat, lon);
        myMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(-18));*/

        //populateCurrentLocation();
    }

    public void populateCurrentLocation(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("My Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    //mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("My Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        iii = 0;
    }
}
