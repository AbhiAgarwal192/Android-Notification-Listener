package com.abhiagarwal.readwhatsappnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.abhiagarwal.readwhatsappnotifications.prefstore.PrefStore;

public class NotificationService extends NotificationListenerService {

    private String TAG = this .getClass().getSimpleName() ;
    private String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    private Context applicationContext;
    private PrefStore prefStore;

    @Override
    public void onCreate(){
        super.onCreate();
        applicationContext = getApplicationContext();
        prefStore = new PrefStore();
    }


    @Override
    public void onNotificationPosted (StatusBarNotification sbn){
        //Log. i ( TAG , "********** onNotificationPosted" ) ;
        if(sbn.getPackageName().toLowerCase().startsWith(WHATSAPP_PACKAGE_NAME)){

            /*
            String ticker ="";
            if(sbn.getNotification().tickerText !=null) {
                ticker = sbn.getNotification().tickerText.toString();
            }
            */
            //Log. i ( TAG , "ID :" + sbn.getId() + " Message: \t " + ticker + " \t " + sbn.getPackageName()) ;

            Bundle extras = sbn.getNotification().extras;
            String title = extras.getString("android.title");
            String text = extras.getCharSequence("android.text") != null ?  extras.getCharSequence("android.text").toString() : null;

            //Log.i(TAG,"*********************Text: "+ text + "Title: " + title);
            //Log.i( TAG, "**********************flags " + sbn.getNotification().flags);
            if(!prefStore.Initialized){
                prefStore.initialize(applicationContext);
            }
            if( (sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) !=0){
                //Log.i( TAG, "**********************group notification " + sbn.getNotification().flags);
                return;
            }
            else{
               // Log.i(TAG,"****************Writing to Pref Store");
                if(text == null){
                    return;
                }
                prefStore.write(applicationContext,title + "  " + text);
            }
        }
    }

    @Override
    public void onNotificationRemoved (StatusBarNotification sbn){
    }
}
