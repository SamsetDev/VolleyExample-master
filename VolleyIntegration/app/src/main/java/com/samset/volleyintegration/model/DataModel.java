package com.samset.volleyintegration.model;

/**
 * Created by samset on 23/03/16.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DataModel {

    @SerializedName("actors")
    @Expose
    private List<Actor> actors = new ArrayList<Actor>();

    /**
     *
     * @return
     * The actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     *
     * @param actors
     * The actors
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

}