package com.example.ricardogarcia.politojobs;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by POLI on 5/11/2015.
 */
public class ParseApplication extends Application
{
    public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
    public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";

    @Override
    public void onCreate() {
        super.onCreate();

        // initialization
        Parse.enableLocalDatastore(this);
        Parse.initialize(getApplicationContext(), APPLICATION_ID, CLIENT_KEY);

    }
}