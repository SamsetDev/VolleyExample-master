package com.samset.volleyintegration.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.samset.volleyintegration.R;
import com.samset.volleyintegration.adapter.RecyclerAdapter;
import com.samset.volleyintegration.model.Actor;
import com.samset.volleyintegration.model.DataModel;
import com.samset.volleyintegration.network.GsonRequest;
import com.samset.volleyintegration.network.VolleySingleton;
import com.samset.volleyintegration.utils.Utilities;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
   private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        if (Utilities.isConnected(this)) {
            callApiGson();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    //set data in RecyclerView
    private void setRecyclerView(List<Actor> data)
    {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void callApiGson() {
    /*
      * In first line you pass input parameter like GsonRequest<YourParentModelclass> and in parameter which type of your api like POST/GET
      * and pass your URL and pass your ParentModel class and pass Successlistener and errorlisteners */

        GsonRequest<DataModel> jsonRequest = new GsonRequest<DataModel>(Request.Method.GET,
                Utilities.URL, DataModel.class, successListener(), errorListener());

        /*
        * In second code is define when you call api and take more time responce then your api auto recall by setRetryPolicy*/

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        /*
        * Bind your api in own volley queue */

        VolleySingleton.getInstance(activity).addToRequestQueue(jsonRequest, "firstApi");


        /*
        * Show progress bar when call your api*/

        Utilities.showProgressDialog(activity, "Loading..");
        //Log.e("MYPostDetails", " new url api " + strData);

    }

    public Response.Listener successListener() {
        return new Response.Listener<DataModel>() {
            @Override
            public void onResponse(final DataModel response) {
                //Check your responce is null or not......This is prevent app crash
                if (response != null) {

                    Utilities.dismissDialog();

                    // get all data in list
                    List<Actor> data=response.getActors();

                    // set data in recyclerview method
                    setRecyclerView(data);
                    Log.e("MainActivity", " Responce Success " + response.getActors().size());

                }
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
