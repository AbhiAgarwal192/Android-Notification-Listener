package com.abhiagarwal.readwhatsappnotifications.prefstore;

import android.content.Context;
import android.content.SharedPreferences;
import com.abhiagarwal.readwhatsappnotifications.R;
import java.util.HashSet;
import java.util.Set;

public class PrefStore implements IPrefStore{

    SharedPreferences sharedPreferences;
    public boolean Initialized;

    public void initialize(Context context){
        sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Initialized = true;
    }

    public Set<String> read(Context context){
        Set<String> values = new HashSet<String>(sharedPreferences.getStringSet(context.getString(R.string.messages), new HashSet<String>()));
        return values;
    }

    public void write(Context context, String message){
        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> values = read(context);
            values.add(message);
            editor.putStringSet(context.getString(R.string.messages),values);
            editor.apply();
        }
    }

    public void delete(Context context, String message){
        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> values = read(context);
            values.remove(message);
            editor.putStringSet(context.getString(R.string.messages),values);
            editor.apply();
        }
    }

    public void clearAll(Context context){
        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> values = read(context);
            values.clear();
            editor.putStringSet(context.getString(R.string.messages),values);
            editor.apply();
        }

    }
}

