package com.synergyinterface.askrambler.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by saim on 3/1/17.
 */

public class SharedPrefDatabase {

    public static final String KEY_GET_ALL_POST = "KEY_GET_ALL_POST";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreGetAllPost(String data){
        editor.putString(KEY_GET_ALL_POST, data);
        editor.commit();
    }
    public String RetriveGetAllPost(){
        String text = sharedPreferences.getString(KEY_GET_ALL_POST, null);
        return text;
    }
}
