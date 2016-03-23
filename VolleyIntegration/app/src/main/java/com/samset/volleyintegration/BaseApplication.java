package com.samset.volleyintegration;

import android.app.Application;

import com.samset.volleyintegration.network.PicassoSingleton;
import com.samset.volleyintegration.network.VolleySingleton;
import com.squareup.picasso.Picasso;

/**
 * Created by samset on 23/03/16.
 */
public class BaseApplication extends Application {
    public static Picasso picasso = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //Initilization of picasso and volley
        VolleySingleton.getInstance(this);
        picasso = PicassoSingleton.getPicassoInstance(this);
    }
}
