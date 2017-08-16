package com.synergyinterface.askrambler.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
import com.synergyinterface.askrambler.Model.ModelPostShort;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentSearchResult extends Fragment {

    View view;

    ProgressDialog progressDialog;

    public ArrayList<ModelPostShort> modelPostsList = new ArrayList<>();
    RecyclerView recyclerAllPost;
    RecyclerView.LayoutManager layoutManagerAllPost;
    RecyclerView.Adapter allPostAdapter;

    public FragmentSearchResult() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);

        init();

        return view;
    }

    public void init() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerAllPost = (RecyclerView) view.findViewById(R.id.recyclerViewAllPostSearch);
        layoutManagerAllPost = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAllPost.setLayoutManager(layoutManagerAllPost);
        recyclerAllPost.setHasFixedSize(true);

        String ad_type = getArguments().getString("ad_type");
        String from_where = getArguments().getString("from_where");
        String to_where = getArguments().getString("to_where");
        String from_date = getArguments().getString("from_date");
        String to_date = getArguments().getString("to_date");
        String gender = getArguments().getString("gender");

        GetAllCompanionPost(ad_type, from_where, to_where, from_date, to_date, gender);

    }


    public void GetAllCompanionPost(final String ad_type1, final String from_where1, final String to_where1, final String from_date1, final String to_date1, final String gender1) {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.searchCompanion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SPLASH 1", ad_type1 + "\n" + from_where1 + "\n" + to_where1 + "\n" + from_date1+ "\n" + to_date1 + "\n" + gender1);
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
                                progressDialog.dismiss();
                                allPostAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPost.setAdapter(allPostAdapter);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ad_type", ad_type1);
                params.put("from_where", from_where1);
                params.put("to_where", to_where1);
                params.put("from_date", from_date1);
                params.put("to_date", to_date1);
                params.put("gender", gender1);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }


}
