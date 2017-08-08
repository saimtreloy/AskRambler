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

public class FragmentAdvancedSearch extends Fragment {

    View view;

    Button btnSPostCom, btnSPostBag, btnSPostTrip, btnSPostHost;
    RelativeLayout layoutSPostCompanion, layoutSPostBaggage, layoutSPostTrip, layoutSPostHost;
    
    public FragmentAdvancedSearch() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        init();
        return view;
    }


    public void init(){
        HomeActivity.toolbar.setTitle("Advanced Search");

        btnSPostCom = (Button) view.findViewById(R.id.btnSPostCom);
        btnSPostBag = (Button) view.findViewById(R.id.btnSPostBag);
        btnSPostTrip = (Button) view.findViewById(R.id.btnSPostTrip);
        btnSPostHost = (Button) view.findViewById(R.id.btnSPostHost);

        layoutSPostCompanion = (RelativeLayout) view.findViewById(R.id.layoutSPostCompanion);
        layoutSPostBaggage = (RelativeLayout) view.findViewById(R.id.layoutSPostBaggage);
        layoutSPostTrip = (RelativeLayout) view.findViewById(R.id.layoutSPostTrip);
        layoutSPostHost = (RelativeLayout) view.findViewById(R.id.layoutSPostHost);

        ButtonClickedTop();
    }

    public void ButtonClickedTop(){

        btnSPostCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostCom.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.VISIBLE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        layoutSPostCompanion.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutSPostCompanion));
            }
        });

        btnSPostBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostBag.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));


                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.VISIBLE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostBaggage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutSPostBaggage));
            }
        });

        btnSPostTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostTrip.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.VISIBLE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostTrip.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutSPostTrip));
            }
        });

        btnSPostHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostHost.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostHost.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.layoutSPostHost));
            }
        });

    }

}
