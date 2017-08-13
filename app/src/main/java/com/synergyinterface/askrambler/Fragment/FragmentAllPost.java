package com.synergyinterface.askrambler.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

public class FragmentAllPost extends Fragment {

    View view;

    RecyclerView recyclerAllPost;
    RecyclerView.LayoutManager layoutManagerAllPost;
    RecyclerView.Adapter allPostAdapter;

    Button btnFabCreatePost;

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

        btnFabCreatePost = (Button) view.findViewById(R.id.btnFabCreatePost);
        btnFabCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new SharedPrefDatabase(getContext()).RetriveLogin()!=null &&new SharedPrefDatabase(getContext()).RetriveLogin().equals("Yes")){
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentAddPost()).addToBackStack(null).commit();
                }else {
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
                }
            }
        });
    }


}
