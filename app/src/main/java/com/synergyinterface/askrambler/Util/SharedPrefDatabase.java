package com.synergyinterface.askrambler.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefDatabase {

    public static final String KEY_LOGIN = "KEY_LOGIN";

    public static final String KEY_USER_FULLNAME = "KEY_USER_FULLNAME";
    public static final String KEY_USER_PHOTO = "KEY_USER_PHOTO";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreLogin(String data){
        editor.putString(KEY_LOGIN, data);
        editor.commit();
    }
    public String RetriveLogin(){
        String text = sharedPreferences.getString(KEY_LOGIN, null);
        return text;
    }

    public void StoreUserFullName(String data){
        editor.putString(KEY_USER_FULLNAME, data);
        editor.commit();
    }
    public String RetriveUserFullName(){
        String text = sharedPreferences.getString(KEY_USER_FULLNAME, null);
        return text;
    }

    public void StoreUserPhoto(String data){
        editor.putString(KEY_USER_PHOTO, data);
        editor.commit();
    }
    public String RetriveUserPhoto(){
        String text = sharedPreferences.getString(KEY_USER_PHOTO, null);
        return text;
    }
}
