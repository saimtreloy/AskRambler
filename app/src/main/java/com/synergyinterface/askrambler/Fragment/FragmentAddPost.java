package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.R;

public class FragmentAddPost extends Fragment {

    View view;

    Button btnAddPostCom, btnAddPostBag, btnAddPostTrip, btnAddPostHost;
    RelativeLayout layoutAddPostCompanion, layoutAddPostBaggage, layoutAddPostTrip, layoutAddPostHost;

    public FragmentAddPost() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        init();

        return view;
    }

    public void init(){
        HomeActivity.toolbar.setTitle("Add Post");

        btnAddPostCom = (Button) view.findViewById(R.id.btnAddPostCom);
        btnAddPostBag = (Button) view.findViewById(R.id.btnAddPostBag);
        btnAddPostTrip = (Button) view.findViewById(R.id.btnAddPostTrip);
        btnAddPostHost = (Button) view.findViewById(R.id.btnAddPostHost);

        layoutAddPostCompanion = (RelativeLayout) view.findViewById(R.id.layoutAddPostCompanion);
        layoutAddPostBaggage = (RelativeLayout) view.findViewById(R.id.layoutAddPostBaggage);
        layoutAddPostTrip = (RelativeLayout) view.findViewById(R.id.layoutAddPostTrip);
        layoutAddPostHost = (RelativeLayout) view.findViewById(R.id.layoutAddPostHost);

        ButtonClickedTop();
    }

    public void ButtonClickedTop(){

        btnAddPostCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostCom.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.VISIBLE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        layoutAddPostCompanion.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutAddPostCompanion));
            }
        });

        btnAddPostBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostBag.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));


                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.VISIBLE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostBaggage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutAddPostBaggage));
            }
        });

        btnAddPostTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostTrip.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.VISIBLE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostTrip.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutAddPostTrip));
            }
        });

        btnAddPostHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostHost.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostHost.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutAddPostHost));
            }
        });

    }

}
