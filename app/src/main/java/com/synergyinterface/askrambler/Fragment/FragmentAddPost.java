package com.synergyinterface.askrambler.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synergyinterface.askrambler.R;

public class FragmentAddPost extends Fragment {

    View view;

    public FragmentAddPost() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        return view;
    }

}
