package com.synergyinterface.askrambler.Fragment;


import android.Manifest;
import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.synergyinterface.askrambler.Activity.HomeActivity;
import com.synergyinterface.askrambler.R;
import com.synergyinterface.askrambler.Util.MySingleton;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class FragmentAddPost extends Fragment {

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCBrJXQuoQXATq461rT-WoaO5Sd0c9aqTQ";
    private static final int PICK_IMAGE_REQUEST=1001;

    View view;
    Button btnAddPostCom, btnAddPostBag, btnAddPostTrip, btnAddPostHost;
    RelativeLayout layoutAddPostCompanion, layoutAddPostBaggage, layoutAddPostTrip, layoutAddPostHost;
    //Google Item
    ArrayList resultList = null;
    ArrayAdapter<String> adapter;
    //Companion
    AutoCompleteTextView inputAddComFrom, inputAddComTo;
    EditText inputAddConExpectedDate, inputAddComPayment, inputAddComGender, inputAddComTravelBy, inputAddComContact,
            inputAddComImage, inputAddComDescription;
    Button btnAddComPostAdd;

    //Baggage
    RadioButton radioAddBagSendBag, radioAddBagCarryBag;
    AutoCompleteTextView inputAddBagFrom, inputAddBagTo;
    EditText inputAddBagExpectedDate, inputAddBagBaggageType, inputAddBagBaggageWeight, inputAddBagPaymentCategory, inputAddBagContactNo,
            inputAddBagImage, inputAddBagDescription;
    Button btnAddBagPostAdd;

    //Trip
    RadioButton radioAddTripArrageTrip, radioAddTripGoOn;
    AutoCompleteTextView inputAddTripFrom, inputAddTripTo;
    EditText inputAddTripExpectedDate, inputAddTripCategory, inputAddTripType,inputAddTripDuration, inputAddTripPayment, inputAddTripContactNo,
            inputAddTripImage, inputAddTripDescription;
    Button btnAddTripPostAdd;

    //Host
    RadioButton radioAddHostWantTo, radioAddHostNeedHost;
    AutoCompleteTextView inputAddHostLocation;
    EditText inputAddHostContactNo, inputAddHostPayment, inputAddHostHabitSmocking, inputAddHostHabitAlcohol, inputAddHostImage, inputAddHostDescription;
    CheckBox checkBoxFood, checkBoxInternet, checkBoxTransport;
    SeekBar seekBarNoTravelerHost;
    TextView txtAddHostNoTraveler;
    Button btnAddHostPostAdd;


    //Image Uploading
    List<Uri> images = new ArrayList<>();

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

        haveStoragePermission();

        btnAddPostCom = (Button) view.findViewById(R.id.btnAddPostCom);
        btnAddPostBag = (Button) view.findViewById(R.id.btnAddPostBag);
        btnAddPostTrip = (Button) view.findViewById(R.id.btnAddPostTrip);
        btnAddPostHost = (Button) view.findViewById(R.id.btnAddPostHost);

        layoutAddPostCompanion = (RelativeLayout) view.findViewById(R.id.layoutAddPostCompanion);
        layoutAddPostBaggage = (RelativeLayout) view.findViewById(R.id.layoutAddPostBaggage);
        layoutAddPostTrip = (RelativeLayout) view.findViewById(R.id.layoutAddPostTrip);
        layoutAddPostHost = (RelativeLayout) view.findViewById(R.id.layoutAddPostHost);

        ButtonClickedTop();

        //Companion
        inputAddComFrom = (AutoCompleteTextView) view.findViewById(R.id.inputAddComFrom);
        inputAddComTo = (AutoCompleteTextView) view.findViewById(R.id.inputAddComTo);

        inputAddConExpectedDate = (EditText) view.findViewById(R.id.inputAddConExpectedDate);
        inputAddComPayment = (EditText) view.findViewById(R.id.inputAddComPayment);
        inputAddComGender = (EditText) view.findViewById(R.id.inputAddComGender);
        inputAddComTravelBy = (EditText) view.findViewById(R.id.inputAddComTravelBy);
        inputAddComContact = (EditText) view.findViewById(R.id.inputAddComContact);
        inputAddComImage = (EditText) view.findViewById(R.id.inputAddComImage);
        inputAddComDescription = (EditText) view.findViewById(R.id.inputAddComDescription);

        btnAddComPostAdd = (Button) view.findViewById(R.id.btnAddComPostAdd);

        CompanionEditTextClick();

        //Baggage
        inputAddBagFrom = (AutoCompleteTextView) view.findViewById(R.id.inputAddBagFrom);
        inputAddBagTo = (AutoCompleteTextView) view.findViewById(R.id.inputAddBagTo);

        radioAddBagSendBag = (RadioButton) view.findViewById(R.id.radioAddBagSendBag);
        radioAddBagCarryBag = (RadioButton) view.findViewById(R.id.radioAddBagCarryBag);

        inputAddBagExpectedDate = (EditText) view.findViewById(R.id.inputAddBagExpectedDate);
        inputAddBagBaggageType = (EditText) view.findViewById(R.id.inputAddBagBaggageType);
        inputAddBagBaggageWeight = (EditText) view.findViewById(R.id.inputAddBagBaggageWeight);
        inputAddBagPaymentCategory = (EditText) view.findViewById(R.id.inputAddBagPaymentCategory);
        inputAddBagContactNo = (EditText) view.findViewById(R.id.inputAddBagContactNo);
        inputAddBagImage = (EditText) view.findViewById(R.id.inputAddBagImage);
        inputAddBagDescription = (EditText) view.findViewById(R.id.inputAddBagDescription);

        btnAddBagPostAdd = (Button) view.findViewById(R.id.btnAddBagPostAdd);

        BaggageEditTextClick();


        //Trip
        inputAddTripFrom = (AutoCompleteTextView) view.findViewById(R.id.inputAddTripFrom);
        inputAddTripTo = (AutoCompleteTextView) view.findViewById(R.id.inputAddTripTo);

        radioAddTripArrageTrip = (RadioButton) view.findViewById(R.id.radioAddTripArrageTrip);
        radioAddTripGoOn = (RadioButton) view.findViewById(R.id.radioAddTripGoOn);

        inputAddTripExpectedDate = (EditText) view.findViewById(R.id.inputAddTripExpectedDate);
        inputAddTripCategory = (EditText) view.findViewById(R.id.inputAddTripCategory);
        inputAddTripType = (EditText) view.findViewById(R.id.inputAddTripType);
        inputAddTripDuration = (EditText) view.findViewById(R.id.inputAddTripDuration);
        inputAddTripPayment = (EditText) view.findViewById(R.id.inputAddTripPayment);
        inputAddTripContactNo = (EditText) view.findViewById(R.id.inputAddTripContactNo);
        inputAddTripImage = (EditText) view.findViewById(R.id.inputAddTripImage);
        inputAddTripDescription = (EditText) view.findViewById(R.id.inputAddTripDescription);

        btnAddTripPostAdd = (Button) view.findViewById(R.id.btnAddTripPostAdd);
        TripEditTextClick();

        //Host
        inputAddHostLocation = (AutoCompleteTextView) view.findViewById(R.id.inputAddHostLocation);

        radioAddHostWantTo = (RadioButton) view.findViewById(R.id.radioAddHostWantTo);
        radioAddHostNeedHost = (RadioButton) view.findViewById(R.id.radioAddHostNeedHost);

        inputAddHostContactNo = (EditText) view.findViewById(R.id.inputAddHostContactNo);
        inputAddHostPayment = (EditText) view.findViewById(R.id.inputAddHostPayment);
        inputAddHostHabitSmocking = (EditText) view.findViewById(R.id.inputAddHostHabitSmocking);
        inputAddHostHabitAlcohol = (EditText) view.findViewById(R.id.inputAddHostHabitAlcohol);
        inputAddHostImage = (EditText) view.findViewById(R.id.inputAddHostImage);
        inputAddHostDescription = (EditText) view.findViewById(R.id.inputAddHostDescription);

        checkBoxFood = (CheckBox) view.findViewById(R.id.checkBoxFood);
        checkBoxInternet = (CheckBox) view.findViewById(R.id.checkBoxInternet);
        checkBoxTransport = (CheckBox) view.findViewById(R.id.checkBoxTransport);

        seekBarNoTravelerHost = (SeekBar) view.findViewById(R.id.seekBarNoTravelerHost);

        txtAddHostNoTraveler = (TextView) view.findViewById(R.id.txtAddHostNoTraveler);

        btnAddHostPostAdd = (Button) view.findViewById(R.id.btnAddHostPostAdd);

        HostEditTextClick();
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

    public void CompanionEditTextClick(){
        inputAddComFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddComFrom);
                }
            }
        });
        inputAddComTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddComTo);
                }
            }
        });
        inputAddConExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddConExpectedDate);
            }
        });
        inputAddComPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select payment category", inputAddComPayment);
            }
        });
        inputAddComGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderList("Select Gender", inputAddComGender);
            }
        });
        inputAddComTravelBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelByList("Select transport type", inputAddComTravelBy);
            }
        });
        inputAddComImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(getContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void BaggageEditTextClick(){
        inputAddBagFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddBagFrom);
                }
            }
        });
        inputAddBagTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddBagTo);
                }
            }
        });

        radioAddBagSendBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddBagSendBag.setChecked(true);
                radioAddBagCarryBag.setChecked(false);
            }
        });
        radioAddBagCarryBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddBagCarryBag.setChecked(true);
                radioAddBagSendBag.setChecked(false);
            }
        });

        inputAddBagExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddBagExpectedDate);
            }
        });

        inputAddBagBaggageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaggageTypeList("Select baggage type", inputAddBagBaggageType);
            }
        });

        inputAddBagPaymentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select payment category", inputAddBagPaymentCategory);
            }
        });
    }

    public void TripEditTextClick(){
        inputAddTripFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddTripFrom);
                }
            }
        });
        inputAddTripTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddTripTo);
                }
            }
        });

        radioAddTripArrageTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddTripArrageTrip.setChecked(true);
                radioAddTripGoOn.setChecked(false);
            }
        });
        radioAddTripGoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddTripGoOn.setChecked(true);
                radioAddTripArrageTrip.setChecked(false);
            }
        });

        inputAddTripExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddTripExpectedDate);
            }
        });

        inputAddTripCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTripCategoryList("Select trip category", inputAddTripCategory);
            }
        });

        inputAddTripType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelByList("Select trip transport type", inputAddTripType);
            }
        });

        inputAddTripPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select trip payment category", inputAddTripPayment);
            }
        });
    }

    public void HostEditTextClick(){
        inputAddHostLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 3) {
                    resultList = new ArrayList<String>();
                    SaveFutureProject(s.toString(), inputAddHostLocation);
                }
            }
        });

        radioAddHostWantTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddHostWantTo.setChecked(true);
                radioAddHostNeedHost.setChecked(false);
            }
        });
        radioAddHostNeedHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddHostNeedHost.setChecked(true);
                radioAddHostWantTo.setChecked(false);
            }
        });

        inputAddHostPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select trip payment category", inputAddHostPayment);
            }
        });

        inputAddHostHabitSmocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSmockingHabitList("Select trip payment category", inputAddHostHabitSmocking);
            }
        });

        inputAddHostHabitAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlcoholHabitList("Select trip payment category", inputAddHostHabitAlcohol);
            }
        });
        seekBarNoTravelerHost.setMax(20);
        seekBarNoTravelerHost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtAddHostNoTraveler.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //Google Autocomplete  Adapter
    public ArrayList SaveFutureProject(String place, final AutoCompleteTextView auto) {
        String url = PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?key=" + API_KEY + "&components=country:bd" + "&input=" + place;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                            // Extract the Place descriptions from the results
                            resultList = new ArrayList(predsJsonArray.length());
                            for (int i = 0; i < predsJsonArray.length(); i++) {
                                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                                System.out.println("============================================================");
                                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                            }
                            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, resultList){
                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    TextView textView = (TextView) super.getView(position, convertView, parent);

                                    //String currentLocation = resultList.get(position).toString();
                                    textView.setTextColor(Color.BLACK);
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_place, 0, 0, 0);

                                    return textView;
                                }
                            };
                            auto.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        return resultList;
    }

    public void showPaymentCategoryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.payment_category));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void showTravelByList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transport_type));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void showBaggageTypeList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.baggage_type));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void showTripCategoryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trip_category));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void showSmockingHabitList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.smoking_habit));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void showAlcoholHabitList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(getContext()).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alcohol_habit));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void dateSelectFromDatePicker(final EditText editText){
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), resultUri);
                    String s = getStringImage(bitmap);
                    Log.d("Saim Image BASE64", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String s = resultUri.toString().substring(resultUri.toString().lastIndexOf("/")+1,resultUri.toString().length());
                inputAddComImage.setText(s);

                Toast.makeText(getContext(), resultUri.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
