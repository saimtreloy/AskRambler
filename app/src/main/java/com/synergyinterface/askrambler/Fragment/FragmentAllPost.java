package com.synergyinterface.askrambler.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
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
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentAllPost extends Fragment {

    View view;

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

    public void init() {
        recyclerAllPost = (RecyclerView) view.findViewById(R.id.recyclerViewAllPost);
        layoutManagerAllPost = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAllPost.setLayoutManager(layoutManagerAllPost);
        recyclerAllPost.setHasFixedSize(true);
        allPostAdapter = new AdapterPost(Splash.modelPostsList);
        recyclerAllPost.setAdapter(allPostAdapter);
    }


}
