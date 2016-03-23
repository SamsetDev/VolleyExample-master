package com.samset.volleyintegration.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonParseException;
import com.samset.volleyintegration.R;
import com.samset.volleyintegration.model.Actor;
import com.samset.volleyintegration.model.DataModel;
import com.samset.volleyintegration.network.MyJsonRequest;
import com.samset.volleyintegration.network.VolleySingleton;
import com.samset.volleyintegration.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SimpleJsonArray_Activity extends AppCompatActivity {
private Activity activity;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_json_array_);
        activity=this;
        callJsonApi();
    }


    public void callJsonApi() throws JsonParseException {

        JSONArray arr = new JSONArray();

        // change your api and get the response


        MyJsonRequest gsonRequest = new MyJsonRequest(Request.Method.GET,
        Utilities.URL, arr, successListener(), errorListener());
        Utilities.showProgressDialog(activity, "Please wait..");
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Start progressbar
        VolleySingleton.getInstance(activity).addToRequestQueue(gsonRequest, "simplejson");
    }

    public Response.Listener successListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    //Log.e("MYPostDetails", " Responce Success data " + response.length());
                    String regepx = "<p></p>";
                    count = response.length();
                    Log.e("JSon", " Responce Success " +count);
                    /*for (int i = 0; i < count; i++) {
                        try {
                            // this is your responce
                            JSONObject object = response.getJSONObject(i);
                            String img_dscription, sub_title, img_path, slide_no, slide_id;
                            img_dscription = object.getString("image_description");
                            sub_title = object.getString("sub_title");
                            img_path = object.getString("image_path");
                            slide_no = object.getString("slide_no");
                            slide_id = object.getString("id");
                            // Log.e("MYPostDetails", " Responce id " + slide_id);
                            TextView description;
                            TextView slide_count;
                            TextView title;
                            ImageView post_image;


                        } catch (JSONException w) {
                            w.printStackTrace();
                        }


                    }*/

                } else {
                    //Log.e("MYPostDetails", " Responce Success data " + "No Data");
                }
                Utilities.dismissDialog();
                //home_pregress.setVisibility(View.GONE);
            }
        };

    }

    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code handle all expectd come volley errors
                Utilities.handleVolleyError(error);

            }
        };

    }
}
