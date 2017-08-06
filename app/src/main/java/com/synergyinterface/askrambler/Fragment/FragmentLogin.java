package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.R;


public class FragmentLogin extends Fragment {

    View view;

    CardView cardLogin, cardSignup;

    //Login Layout Attributes
    EditText inputLoginEmailOrMobile, inputLoginPassword;
    Button btnLoginLogin;
    TextView txtLoginForgetPassword, txtLoginSignup;

    //Signup Layout Attributes
    TextView txtRegSignin;
    EditText inputRegFullName, inputRegEmail, inputRegMobile, inputRegAddress, inputRegPassword;
    Button btnRegSignUp;

    public FragmentLogin() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        init();

        return view;
    }

    public void init(){
        cardLogin = (CardView) view.findViewById(R.id.cardLogin);
        cardSignup = (CardView) view.findViewById(R.id.cardSignup);

        //Login layout
        inputLoginEmailOrMobile = (EditText) view.findViewById(R.id.inputLoginEmailOrMobile);
        inputLoginPassword = (EditText) view.findViewById(R.id.inputLoginPassword);
        btnLoginLogin = (Button) view.findViewById(R.id.btnLoginLogin);
        txtLoginForgetPassword = (TextView) view.findViewById(R.id.txtLoginForgetPassword);
        txtLoginSignup = (TextView) view.findViewById(R.id.txtLoginSignup);

        //Signup Layout
        txtRegSignin = (TextView) view.findViewById(R.id.txtRegSignin);
        inputRegFullName = (EditText) view.findViewById(R.id.inputRegFullName);
        inputRegEmail = (EditText) view.findViewById(R.id.inputRegEmail);
        inputRegMobile = (EditText) view.findViewById(R.id.inputRegMobile);
        inputRegAddress = (EditText) view.findViewById(R.id.inputRegAddress);
        inputRegPassword = (EditText) view.findViewById(R.id.inputRegPassword);
        btnRegSignUp = (Button) view.findViewById(R.id.btnRegSignUp);

        ButtonAction();
    }


    public void ButtonAction(){
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutRight).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cardLogin.setVisibility(View.GONE);
                        cardSignup.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft).duration(250).playOn(view.findViewById(R.id.cardSignup));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.cardLogin));
            }
        });


        txtRegSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutLeft).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cardSignup.setVisibility(View.GONE);
                        cardLogin.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInRight).duration(250).playOn(view.findViewById(R.id.cardLogin));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(view.findViewById(R.id.cardSignup));
            }
        });

    }
}
