package com.synergyinterface.askrambler.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by saim on 3/1/17.
 */

public class SharedPrefDatabase {

    public static final String KEY_GET_LOGIN_INFO = "KEY_GET_LOGIN_INFO";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreGetLoginInfo(String data){
        editor.putString(KEY_GET_LOGIN_INFO, data);
        editor.commit();
    }
    public String RetriveGetLoginInfo(){
        String text = sharedPreferences.getString(KEY_GET_LOGIN_INFO, null);
        return text;
    }
}
