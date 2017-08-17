package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.R;

public class FragmentProfile extends Fragment {

    View view;

    RelativeLayout layoutProProfile ,layoutProChangePasword;
    TextView txtProProfile, txtProChangePassword;

    ImageView imgProfileImage;
    EditText inputProFullName, inputProEmail, inputProGender, inputProCountry, inputProState, inputProCity,
            inputProZip, inputProAddress, inputProBirthday, inputProMobile, inputProPhone, inputProLikeTo,
            inputProFacebook, inputProInstagram, inputProYoutube, inputProDetail;
    Button btnLoginLogin;

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

        //Profile Information
        imgProfileImage = (ImageView) view.findViewById(R.id.imgProfileImage);

        inputProFullName = (EditText) view.findViewById(R.id.inputProFullName);
        inputProEmail = (EditText) view.findViewById(R.id.inputProEmail);
        inputProGender = (EditText) view.findViewById(R.id.inputProGender);
        inputProCountry = (EditText) view.findViewById(R.id.inputProCountry);
        inputProState = (EditText) view.findViewById(R.id.inputProState);
        inputProCity = (EditText) view.findViewById(R.id.inputProCity);
        inputProZip = (EditText) view.findViewById(R.id.inputProZip);
        inputProAddress = (EditText) view.findViewById(R.id.inputProAddress);
        inputProBirthday = (EditText) view.findViewById(R.id.inputProBirthday);
        inputProMobile = (EditText) view.findViewById(R.id.inputProMobile);
        inputProPhone = (EditText) view.findViewById(R.id.inputProPhone);
        inputProLikeTo = (EditText) view.findViewById(R.id.inputProLikeTo);
        inputProFacebook = (EditText) view.findViewById(R.id.inputProFacebook);
        inputProInstagram = (EditText) view.findViewById(R.id.inputProInstagram);
        inputProYoutube = (EditText) view.findViewById(R.id.inputProYoutube);
        inputProDetail = (EditText) view.findViewById(R.id.inputProDetail);

        btnLoginLogin = (Button) view.findViewById(R.id.btnLoginLogin);

        PopulateProfileInformation();
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

    public void PopulateProfileInformation(){
        Glide.with(getContext())
                .load(Splash.user_photo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgProfileImage);
        inputProFullName.setText(Splash.full_name);
        inputProEmail.setText(Splash.email);
        inputProGender.setText(Splash.gander);
        inputProCountry.setText(Splash.country);
        inputProState.setText(Splash.state);
        inputProCity.setText(Splash.city);
        inputProZip.setText(Splash.zip);
        inputProAddress.setText(Splash.address);
        inputProBirthday.setText(Splash.birth_date);
        inputProMobile.setText(Splash.mobile);
        inputProPhone.setText(Splash.phone);
        inputProLikeTo.setText(Splash.like_to);
        inputProFacebook.setText(Splash.facebook);
        inputProInstagram.setText(Splash.instagram);
        inputProYoutube.setText(Splash.youtube);
        inputProDetail.setText(Splash.details);

    }

}
