package com.synergyinterface.askrambler.Fragment;


import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.Model.ModelPostShort;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.CircleTransform;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.synergyinterface.askrambler.Util.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FragmentProfile extends Fragment {

    View view;

    ProgressDialog progressDialog;
    RelativeLayout layoutProProfile ,layoutProChangePasword;
    TextView txtProProfile, txtProChangePassword, inputProFullName, inputProEmail, inputProMobile;
    ImageView imgProfileImage;
    EditText inputProGender, inputProCountry, inputProState, inputProCity,
            inputProZip, inputProAddress, inputProBirthday, inputProPhone, inputProLikeTo,
            inputProFacebook, inputProInstagram, inputProYoutube, inputProDetail;
    Button btnLoginLogin;

    ArrayList<String> countryList = new ArrayList<>();

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

        progressDialog = new ProgressDialog(getContext());

        layoutProProfile = (RelativeLayout) view.findViewById(R.id.layoutProProfile);
        layoutProChangePasword = (RelativeLayout) view.findViewById(R.id.layoutProChangePasword);

        txtProProfile = (TextView) view.findViewById(R.id.txtProProfile);
        txtProChangePassword = (TextView) view.findViewById(R.id.txtProChangePassword);

        ButtonClicked();

        //Profile Information
        imgProfileImage = (ImageView) view.findViewById(R.id.imgProfileImage);

        inputProFullName = (TextView) view.findViewById(R.id.inputProFullName);
        inputProEmail = (TextView) view.findViewById(R.id.inputProEmail);
        inputProGender = (EditText) view.findViewById(R.id.inputProGender);
        inputProCountry = (EditText) view.findViewById(R.id.inputProCountry);
        inputProState = (EditText) view.findViewById(R.id.inputProState);
        inputProCity = (EditText) view.findViewById(R.id.inputProCity);
        inputProZip = (EditText) view.findViewById(R.id.inputProZip);
        inputProAddress = (EditText) view.findViewById(R.id.inputProAddress);
        inputProBirthday = (EditText) view.findViewById(R.id.inputProBirthday);
        inputProMobile = (TextView) view.findViewById(R.id.inputProMobile);
        inputProPhone = (EditText) view.findViewById(R.id.inputProPhone);
        inputProLikeTo = (EditText) view.findViewById(R.id.inputProLikeTo);
        inputProFacebook = (EditText) view.findViewById(R.id.inputProFacebook);
        inputProInstagram = (EditText) view.findViewById(R.id.inputProInstagram);
        inputProYoutube = (EditText) view.findViewById(R.id.inputProYoutube);
        inputProDetail = (EditText) view.findViewById(R.id.inputProDetail);

        btnLoginLogin = (Button) view.findViewById(R.id.btnLoginLogin);

        PopulateProfileInformation();
        InputFieldClicked();
    }

    public void InputFieldClicked(){
        inputProCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Fetching country list.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                GetCountryList(inputProCountry);
            }
        });

        inputProBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputProBirthday);
            }
        });

        inputProGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderList("Gender List", inputProGender);
            }
        });
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
                .load(Splash.user_photo).transform(new CircleTransform(getContext()))
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

    public void showGenderList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String) parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void dateSelectFromDatePicker(final EditText editText) {
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void showCountryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countryList);
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String) parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void GetCountryList(final EditText editText) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.countryList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("countryCodes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                countryList.add(jsonObject1.getString("country_name"));
                            }

                        } catch (Exception e) {

                        }
                        showCountryList("Country List", editText);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
