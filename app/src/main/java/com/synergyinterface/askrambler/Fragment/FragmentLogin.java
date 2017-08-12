package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
    EditText inputRegFullName, inputRegEmail, inputRegMobile, inputRegPassword;
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
        inputRegFullName = (EditText) view.findViewById(R.id.inputRegFullName);
        inputRegEmail = (EditText) view.findViewById(R.id.inputRegEmail);
        inputRegMobile = (EditText) view.findViewById(R.id.inputRegMobile);
        inputRegPassword = (EditText) view.findViewById(R.id.inputRegPassword);
        btnRegSignUp = (Button) view.findViewById(R.id.btnRegSignUp);

        ButtonAction();
        SaveUserLogin("asasa", "sasa");
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);

                                String user_id = jsonObjectList.getString("user_id");
                                String nationality = jsonObjectList.getString("nationality");
                                String full_name = jsonObjectList.getString("full_name");
                                String email = jsonObjectList.getString("email");
                                String password = jsonObjectList.getString("password");
                                String agreement = jsonObjectList.getString("agreement");
                                String status = jsonObjectList.getString("status");
                                String roll = jsonObjectList.getString("roll");
                                String first_name = jsonObjectList.getString("first_name");
                                String last_name = jsonObjectList.getString("last_name");
                                String gander = jsonObjectList.getString("gander");
                                String address = jsonObjectList.getString("address");
                                String city = jsonObjectList.getString("city");
                                String zip = jsonObjectList.getString("zip");
                                String state = jsonObjectList.getString("state");
                                String country = jsonObjectList.getString("country");
                                String mobile = jsonObjectList.getString("mobile");
                                String phone = jsonObjectList.getString("phone");
                                String birth_date = jsonObjectList.getString("birth_date");
                                String user_photo = jsonObjectList.getString("user_photo");
                                String document = jsonObjectList.getString("document");
                                String verify = jsonObjectList.getString("verify");
                                String website = jsonObjectList.getString("website");
                                String facebook = jsonObjectList.getString("facebook");
                                String instagram = jsonObjectList.getString("instagram");
                                String youtube = jsonObjectList.getString("youtube");
                                String code1 = jsonObjectList.getString("code");
                                String cornjob = jsonObjectList.getString("cornjob");
                                String like_to = jsonObjectList.getString("like_to");
                                String details = jsonObjectList.getString("details");
                                String server_date = jsonObjectList.getString("server_date");

                                new SharedPrefDatabase(getContext()).StoreLogin("Yes");
                                new SharedPrefDatabase(getContext()).StoreUserFullName(full_name);
                                new SharedPrefDatabase(getContext()).StoreUserPhoto(user_photo);

                                getContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.ChangeLayoutOnLogin"));

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
}
