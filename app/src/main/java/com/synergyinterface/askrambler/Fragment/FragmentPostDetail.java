package com.synergyinterface.askrambler.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.Activity.Splash;
import com.synergyinterface.askrambler.Adapter.AdapterPost;
import com.synergyinterface.askrambler.Model.ModelPostShort;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.ApiURL;
import com.synergyinterface.askrambler.Util.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentPostDetail extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    View view;

    ProgressDialog progressDialog;
    private SliderLayout mDemoSlider;
    public List<String> bannerImage = new ArrayList<>();

    String from_where, to_where, from_date, to_date, payment_category, gender, traveling_by, isType,
            baggage_type, baggage_weight, trip_category, trip_category_id, transport_type, trip_duration,
            location, travelers, offers_1, offers_2, offers_3, smoking_habit, alcohol_habit, images,
            contacts, details, ad_type, ad_type_id, date, status, user_id, user_photo, vote_count;

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

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);

        SaveGetPostInformation();
    }

    public void PopulateSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < bannerImage.size(); i++) {
            url_maps.put("Image " + i + 1, bannerImage.get(i));
        }
        /*url_maps.put("Hannibal", "https://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");*/

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
        mDemoSlider.setDuration(4000);
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
                        progressDialog.dismiss();
                        try {
                            Log.d("SAIM RESPONSE", response);
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
                            }
                            PopulateSlider();
                            Log.d("Saim Link", bannerImage.toString());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
