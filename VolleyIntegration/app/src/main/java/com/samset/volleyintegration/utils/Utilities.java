package com.samset.volleyintegration.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by samset on 23/03/16.
 */
public class Utilities {

    static ProgressDialog progressDialog;

    public static String JSON_URL="your url";
    public static String BASE_URL="http://microblogging.wingnity.com/";
    public static String URL=BASE_URL+"JSONParsingTutorial/jsonActors";


    public static void showProgressDialog(Activity ctx, String msg) {


        progressDialog = new ProgressDialog(ctx.getWindow().getContext());
        progressDialog.getLayoutInflater();
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void dismissDialog() {
        progressDialog.dismiss();
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static void handleVolleyError(VolleyError error) {
        //if any error occurs in the network operations, show the TextView that contains the error message
        String str;
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            str = "Oops! Your Connection Timed Out, Seems there is no Network !!!";
            //getResources(R.string.error_timeout).toString();

        } else if (error instanceof AuthFailureError) {
            str = "Oops! Saral Vaastu Says It Doesn\'t Recognize You";
            //mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            str = "Oops! Saral Vaastu Server Just Messed Up";
            // mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            str = "Oops! Your Connection Timed Out May be Network Messed Up";
            //TODO
        } else if (error instanceof ParseError) {
            str = "Oops! Data Received Was An Unreadable Mess";
            //TODO
        }

    }

}
