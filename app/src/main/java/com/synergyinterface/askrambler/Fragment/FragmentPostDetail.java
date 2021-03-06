package com.synergyinterface.askrambler.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentPostDetail extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    View view;

    ProgressDialog progressDialog;
    LinearLayout layoutPostDetailMain;
    private SliderLayout mDemoSlider;
    public List<String> bannerImage = new ArrayList<>();

    String from_where, to_where, from_date, to_date, payment_category, gender, traveling_by, isType,
            baggage_type, baggage_weight, trip_category, trip_category_id, transport_type, trip_duration,
            location, travelers, offers_1, offers_2, offers_3, smoking_habit, alcohol_habit, images,
            contacts, details, ad_type, ad_type_id, date, status, user_id, user_photo, vote_count, user_name, user_email, user_phone, user_verify;

    public LinearLayout layoutDetailCompanion, layoutDetailBaggage, layoutDetailTrip, layoutDetailHost;
    public TextView txtPDDetail, txtPDLocation, txtPDService, txtPDHost, txtPDTraveler, txtPDOffering,
            txtPDSmocking, txtPDAlcohol, txtPDUserName, txtPDUserEmail, txtPDUserPhone;
    //Companion Textview
    public TextView txtPDCompDestination, txtPDCompTravelDate, txtPDCompServiceType, txtPDCompGender, txtPDCompTravelBy;
    //Baggage Textview
    public TextView txtPDBagDestination, txtPDBagTravelDate, txtPDBagServiceType, txtPDBagBaggage, txtPDBagBaggageType, txtPDBagBaggageWeight;
    //Baggage Textview
    public TextView txtPDTripDestination, txtPDTripTravelDate, txtPDTripServiceType, txtPDTripTrip, txtPDTripCategory, txtPDTripType, txtPDTripDuration;
    public ImageView imgPDStatus, imgPDUser;
    public RatingBar ratingBar;

    public FragmentPostDetail() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        Log.d("SAIM POST ID2 ", AdapterPost.post_id);
        init();

        return view;
    }

    public void init() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait data is loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        layoutPostDetailMain = (LinearLayout) view.findViewById(R.id.layoutPostDetailMain);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);

        layoutDetailCompanion = (LinearLayout) view.findViewById(R.id.layoutDetailCompanion);
        layoutDetailBaggage = (LinearLayout) view.findViewById(R.id.layoutDetailBaggage);
        layoutDetailTrip = (LinearLayout) view.findViewById(R.id.layoutDetailTrip);
        layoutDetailHost = (LinearLayout) view.findViewById(R.id.layoutDetailHost);

        txtPDDetail = (TextView) view.findViewById(R.id.txtPDDetail);
        txtPDLocation = (TextView) view.findViewById(R.id.txtPDLocation);
        txtPDService = (TextView) view.findViewById(R.id.txtPDService);
        txtPDHost = (TextView) view.findViewById(R.id.txtPDHost);
        txtPDTraveler = (TextView) view.findViewById(R.id.txtPDTraveler);
        txtPDOffering = (TextView) view.findViewById(R.id.txtPDOffering);
        txtPDSmocking = (TextView) view.findViewById(R.id.txtPDSmocking);
        txtPDAlcohol = (TextView) view.findViewById(R.id.txtPDAlcohol);
        txtPDUserName = (TextView) view.findViewById(R.id.txtPDUserName);
        txtPDUserEmail = (TextView) view.findViewById(R.id.txtPDUserEmail);
        txtPDUserPhone = (TextView) view.findViewById(R.id.txtPDUserPhone);

        imgPDStatus = (ImageView) view.findViewById(R.id.imgPDStatus);
        imgPDUser = (ImageView) view.findViewById(R.id.imgPDUser);

        //Companion
        txtPDCompDestination = (TextView) view.findViewById(R.id.txtPDCompDestination);
        txtPDCompTravelDate = (TextView) view.findViewById(R.id.txtPDCompTravelDate);
        txtPDCompServiceType = (TextView) view.findViewById(R.id.txtPDCompServiceType);
        txtPDCompGender = (TextView) view.findViewById(R.id.txtPDCompGender);
        txtPDCompTravelBy = (TextView) view.findViewById(R.id.txtPDCompTravelBy);

        //Baggage
        txtPDBagDestination = (TextView) view.findViewById(R.id.txtPDBagDestination);
        txtPDBagTravelDate = (TextView) view.findViewById(R.id.txtPDBagTravelDate);
        txtPDBagServiceType = (TextView) view.findViewById(R.id.txtPDBagServiceType);
        txtPDBagBaggage = (TextView) view.findViewById(R.id.txtPDBagBaggage);
        txtPDBagBaggageType = (TextView) view.findViewById(R.id.txtPDBagBaggageType);
        txtPDBagBaggageWeight = (TextView) view.findViewById(R.id.txtPDBagBaggageWeight);

        //Trip
        txtPDTripDestination = (TextView) view.findViewById(R.id.txtPDTripDestination);
        txtPDTripTravelDate = (TextView) view.findViewById(R.id.txtPDTripTravelDate);
        txtPDTripServiceType = (TextView) view.findViewById(R.id.txtPDTripServiceType);
        txtPDTripTrip = (TextView) view.findViewById(R.id.txtPDTripTrip);
        txtPDTripCategory = (TextView) view.findViewById(R.id.txtPDTripCategory);
        txtPDTripType = (TextView) view.findViewById(R.id.txtPDTripType);
        txtPDTripDuration = (TextView) view.findViewById(R.id.txtPDTripDuration);



        SaveGetPostInformation();
    }

    public void PopulateInformationCompanion(){
        txtPDDetail.setText(details);

        txtPDCompDestination.setText(to_where);
        txtPDCompTravelDate.setText(from_date);
        txtPDCompServiceType.setText(payment_category);
        txtPDCompGender.setText(gender);
        txtPDCompTravelBy.setText(traveling_by);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getContext())
                .load(user_photo)
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
                .into(imgPDUser);
    }

    public void PopulateInformationBaggage(){
        txtPDDetail.setText(details);

        txtPDBagDestination.setText(to_where);
        txtPDBagTravelDate.setText(from_date);
        txtPDBagServiceType.setText(payment_category);
        txtPDBagBaggage.setText(isType);
        txtPDBagBaggageType.setText(baggage_type);
        txtPDBagBaggageWeight.setText(baggage_weight);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getContext())
                .load(user_photo)
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
                .into(imgPDUser);
    }

    public void PopulateInformationTrip(){
        txtPDDetail.setText(details);

        txtPDTripDestination.setText(to_where);
        txtPDTripTravelDate.setText(from_date);
        txtPDTripServiceType.setText(payment_category);
        txtPDTripTrip.setText(isType);
        txtPDTripCategory.setText(trip_category);
        txtPDTripType.setText(transport_type);
        txtPDTripDuration.setText(trip_duration);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getContext())
                .load(user_photo)
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
                .into(imgPDUser);
    }

    public void PopulateInformationHost(){
        txtPDDetail.setText(details);
        txtPDLocation.setText(to_where);
        txtPDService.setText(payment_category);
        txtPDHost.setText(isType);
        txtPDTraveler.setText(travelers);
        txtPDOffering.setText(offers_1);
        txtPDSmocking.setText(smoking_habit);
        txtPDAlcohol.setText(alcohol_habit);
        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getContext())
                .load(user_photo)
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
                .into(imgPDUser);
    }

    public void PopulateSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < bannerImage.size(); i++) {
            url_maps.put("Image " + i + 1, bannerImage.get(i));
            Log.d("IMAGE LINK", bannerImage.get(i));
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(8000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "" + slider.getUrl(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void StringToList(String s) {
        for (; ; ) {
            if (s.contains(",")) {
                String a = s.substring(0, s.indexOf(","));
                s = s.replace(a + ",", "");
                bannerImage.add("http://askrambler.com/uploads/" + a);
            } else {
                bannerImage.add("http://askrambler.com/uploads/" + s);
                break;
            }
        }
    }

    public void SaveGetPostInformation() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPostDetail+AdapterPost.post_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM RESPONSE", response);
                        progressDialog.dismiss();
                        layoutPostDetailMain.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);
                                from_where = jsonObjectList.getString("from_where");
                                to_where = jsonObjectList.getString("to_where");
                                from_date = jsonObjectList.getString("from_date");
                                to_date = jsonObjectList.getString("to_date");
                                payment_category = jsonObjectList.getString("payment_category");
                                gender = jsonObjectList.getString("gender");
                                traveling_by = jsonObjectList.getString("traveling_by");
                                isType = jsonObjectList.getString("isType");
                                baggage_type = jsonObjectList.getString("baggage_type");
                                baggage_weight = jsonObjectList.getString("baggage_weight");
                                trip_category = jsonObjectList.getString("trip_category");
                                trip_category_id = jsonObjectList.getString("trip_category_id");
                                transport_type = jsonObjectList.getString("transport_type");
                                trip_duration = jsonObjectList.getString("trip_duration");
                                location = jsonObjectList.getString("location");
                                travelers = jsonObjectList.getString("travelers");
                                offers_1 = jsonObjectList.getString("offers_1");
                                offers_2 = jsonObjectList.getString("offers_2");
                                offers_3 = jsonObjectList.getString("offers_3");
                                smoking_habit = jsonObjectList.getString("smoking_habit");
                                alcohol_habit = jsonObjectList.getString("alcohol_habit");
                                images = jsonObjectList.getString("images");
                                StringToList(images);
                                contacts = jsonObjectList.getString("contacts");
                                details = jsonObjectList.getString("details");
                                ad_type = jsonObjectList.getString("ad_type");
                                ad_type_id = jsonObjectList.getString("ad_type_id");
                                date = jsonObjectList.getString("date");
                                status = jsonObjectList.getString("status");
                                user_id = jsonObjectList.getString("user_id");
                                user_photo = jsonObjectList.getString("user_photo");
                                vote_count = jsonObjectList.getString("vote_count");
                                user_name = jsonObjectList.getString("user_name");
                                user_email = jsonObjectList.getString("user_email");
                                user_phone = jsonObjectList.getString("user_phone");
                                user_verify = jsonObjectList.getString("user_verify");
                            }
                            PopulateSlider();
                            if (ad_type.equals("Companion")){
                                layoutDetailCompanion.setVisibility(View.VISIBLE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationCompanion();
                            } else if (ad_type.equals("Host")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.VISIBLE);
                                PopulateInformationHost();
                            } else if (ad_type.equals("Baggage")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.VISIBLE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationBaggage();
                            } else if (ad_type.equals("Trip")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.VISIBLE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationTrip();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
