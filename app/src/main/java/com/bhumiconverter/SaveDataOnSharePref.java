package com.bhumiconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

public class SaveDataOnSharePref {
    public void setData( @NonNull Context context, String TAG, String data){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG, data);
        editor.apply();
    }
    public String getData(@NonNull Context context, String TAG,String defaultData){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String position = preferences.getString(TAG,defaultData);
        return String.valueOf(position);
    }
}
