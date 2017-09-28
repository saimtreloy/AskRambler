package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentLogin extends Fragment {

    View view;

    ProgressDialog progressDialog;
    CardView cardLogin, cardSignup;

    //Login Layout Attributes
    EditText inputLoginEmailOrMobile, inputLoginPassword;
    Button btnLoginLogin;
    TextView txtLoginForgetPassword, txtLoginSignup;

    //Signup Layout Attributes
    TextView txtRegSignin;
    EditText inputRegFirstName, inputRegLastName, inputRegEmail, inputRegMobile, inputRegPassword, inputRegPasswordC;
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
        HomeActivity.toolbar.setTitle("User Login");

        progressDialog = new ProgressDialog(getContext());
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
        inputRegFirstName = (EditText) view.findViewById(R.id.inputRegFirstName);
        inputRegLastName = (EditText) view.findViewById(R.id.inputRegLastName);
        inputRegEmail = (EditText) view.findViewById(R.id.inputRegEmail);
        inputRegMobile = (EditText) view.findViewById(R.id.inputRegMobile);
        inputRegPassword = (EditText) view.findViewById(R.id.inputRegPassword);
        inputRegPasswordC = (EditText) view.findViewById(R.id.inputRegPasswordC);
        btnRegSignUp = (Button) view.findViewById(R.id.btnRegSignUp);

        ButtonAction();
        //SaveUserLogin("asasa", "sasa");
    }


    public void ButtonAction(){
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputLoginEmailOrMobile.getText().toString().isEmpty() && !inputLoginPassword.getText().toString().isEmpty()){
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    SaveUserLogin(inputLoginEmailOrMobile.getText().toString(), inputLoginPassword.getText().toString());
                }else {
                    Toast.makeText(getContext(), "Email or Password filed can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String fname = inputRegFirstName.getText().toString();
                String lname = inputRegLastName.getText().toString();
                String email = inputRegEmail.getText().toString();
                String phone = inputRegMobile.getText().toString();
                String password = inputRegPassword.getText().toString();
                String passwordC = inputRegPasswordC.getText().toString();

                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordC.isEmpty()){
                    Toast.makeText(getContext(), "Input field can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    if (password.equals(passwordC) && password.length()>6){
                        UserRegistration(fname, lname, email, phone, password);
                    }else {
                        Toast.makeText(getContext(), "Password not matched or short!", Toast.LENGTH_SHORT).show();
                    }
                }
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


    public void SaveUserLogin(final String email, final String pass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("SAIM RESPONSE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);

                                Splash.user_id = jsonObjectList.getString("user_id");
                                Splash.nationality = jsonObjectList.getString("nationality");
                                Splash.full_name = jsonObjectList.getString("full_name");
                                Splash.email = jsonObjectList.getString("email");
                                Splash.password = jsonObjectList.getString("password");
                                Splash.agreement = jsonObjectList.getString("agreement");
                                Splash.status = jsonObjectList.getString("status");
                                Splash.roll = jsonObjectList.getString("roll");
                                Splash.first_name = jsonObjectList.getString("first_name");
                                Splash.last_name = jsonObjectList.getString("last_name");
                                Splash.gander = jsonObjectList.getString("gander");
                                Splash.address = jsonObjectList.getString("address");
                                Splash.city = jsonObjectList.getString("city");
                                Splash.zip = jsonObjectList.getString("zip");
                                Splash.state = jsonObjectList.getString("state");
                                Splash.country = jsonObjectList.getString("country");
                                Splash.mobile = jsonObjectList.getString("mobile");
                                Splash.phone = jsonObjectList.getString("phone");
                                Splash.birth_date = jsonObjectList.getString("birth_date");
                                Splash.user_photo = jsonObjectList.getString("user_photo");
                                Splash.document = jsonObjectList.getString("document");
                                Splash.verify = jsonObjectList.getString("verify");
                                Splash.website = jsonObjectList.getString("website");
                                Splash.facebook = jsonObjectList.getString("facebook");
                                Splash.instagram = jsonObjectList.getString("instagram");
                                Splash.youtube = jsonObjectList.getString("youtube");
                                Splash.code1 = jsonObjectList.getString("code");
                                Splash.cornjob = jsonObjectList.getString("cornjob");
                                Splash.like_to = jsonObjectList.getString("like_to");
                                Splash.details = jsonObjectList.getString("details");
                                Splash.server_date = jsonObjectList.getString("server_date");

                                new SharedPrefDatabase(getContext()).StoreLogin("Yes");
                                new SharedPrefDatabase((getContext())).StoreUserEmail(email);
                                new SharedPrefDatabase(getContext()).StoreUserPassword(pass);
                                new SharedPrefDatabase(getContext()).StoreUserFullName(Splash.full_name);
                                new SharedPrefDatabase(getContext()).StoreUserPhoto(Splash.user_photo);

                                getContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.ChangeLayoutOnLogin"));
                                ((FragmentActivity) getContext()).getSupportFragmentManager().popBackStack();
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", pass);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    public void UserRegistration(final String first_name, final String last_name, final String email, final String phone, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.userRegistration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
