package com.synergyinterface.askrambler;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
import com.synergyinterface.askrambler.Model.ModelPost;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentAllPost extends Fragment {

    View view;

    ArrayList<ModelPost> modelPostsList = new ArrayList<>();
    RecyclerView recyclerAllPost;
    RecyclerView.LayoutManager layoutManagerAllPost;
    RecyclerView.Adapter allPostAdapter;

    public FragmentAllPost() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_post, container, false);

        init();

        return view;
    }

    public void init(){
        recyclerAllPost = (RecyclerView) view.findViewById(R.id.recyclerViewAllPost);
        layoutManagerAllPost = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAllPost.setLayoutManager(layoutManagerAllPost);
        recyclerAllPost.setHasFixedSize(true);

        PopulateList();
    }


    public void PopulateList(){
        String response = new SharedPrefDatabase(getContext()).RetriveGetAllPost();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.getString("code");
            if (code.equals("success")){
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i=0; i<jsonArray.length(); i++){

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

                allPostAdapter = new AdapterPost(modelPostsList);
                recyclerAllPost.setAdapter(allPostAdapter);
            }
            startActivity(new Intent(getContext(), HomeActivity.class));
        }catch (Exception e){

        }
    }

}
