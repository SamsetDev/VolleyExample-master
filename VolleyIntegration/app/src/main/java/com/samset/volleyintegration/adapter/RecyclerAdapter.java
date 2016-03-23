package com.samset.volleyintegration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samset.volleyintegration.BaseApplication;
import com.samset.volleyintegration.R;
import com.samset.volleyintegration.model.Actor;
import com.samset.volleyintegration.model.DataModel;

import java.util.HashSet;
import java.util.List;

/**
 * Created by samset on 23/03/16.
 */
public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    Context context;
    List<Actor> itemList;
    String strDomain;
    public RecyclerAdapter(Context contectxxt, List<Actor> item) {
        this.context = contectxxt;
        this.itemList = item;


    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        holder = new MyHolder(v);

        return holder;

    }


    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        Actor actor=itemList.get(position);
        String imgUrl=actor.getImage();

        //download image from server to using picasso
        BaseApplication.picasso.load(imgUrl).placeholder(R.mipmap.ic_launcher).resize(100,100).error(R.mipmap.ic_launcher).into(holder.img);
        holder.name.setText(actor.getName());
        holder.dob.setText(actor.getDob());
        holder.country.setText(actor.getCountry());

    }


    @Override
    public int getItemCount() {

        if (itemList == null || itemList.size() == 0) {
            return 0;
        }

        // +1 for loader
        return itemList.size();

    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView dob;
        public TextView country;
        public ImageView img;


        public MyHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            dob = (TextView) itemView.findViewById(R.id.dob);
            country = (TextView) itemView.findViewById(R.id.country);
            img = (ImageView) itemView.findViewById(R.id.img);



        }
    }


}