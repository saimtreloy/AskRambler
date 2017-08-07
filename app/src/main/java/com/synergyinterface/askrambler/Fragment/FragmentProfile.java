package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.R;

public class FragmentProfile extends Fragment {

    View view;

    RelativeLayout layoutProProfile ,layoutProChangePasword;
    TextView txtProProfile, txtProChangePassword;

    public FragmentProfile() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();

        return view;
    }

    public void init(){
        HomeActivity.toolbar.setTitle("Profile");

        layoutProProfile = (RelativeLayout) view.findViewById(R.id.layoutProProfile);
        layoutProChangePasword = (RelativeLayout) view.findViewById(R.id.layoutProChangePasword);

        txtProProfile = (TextView) view.findViewById(R.id.txtProProfile);
        txtProChangePassword = (TextView) view.findViewById(R.id.txtProChangePassword);

        ButtonClicked();
    }

    public void ButtonClicked(){
        txtProProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutProChangePasword.setVisibility(View.GONE);
                        layoutProProfile.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInUp).duration(250).playOn(view.findViewById(R.id.layoutProProfile));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutProChangePasword));
            }
        });

        txtProChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutProChangePasword.setVisibility(View.VISIBLE);
                        layoutProProfile.setVisibility(View.GONE);
                        YoYo.with(Techniques.SlideInUp).duration(250).playOn(view.findViewById(R.id.layoutProChangePasword));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutProProfile));
            }
        });
    }

}
