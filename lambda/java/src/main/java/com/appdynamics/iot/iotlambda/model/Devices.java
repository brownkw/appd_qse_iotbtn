
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {

    @SerializedName("CastVote")
    @Expose
    private String castVote;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Devices() {
    }

    /**
     * 
     * @param castVote
     */
    public Devices(String castVote) {
        super();
        this.castVote = castVote;
    }

    public String getCastVote() {
        return castVote;
    }

    public void setCastVote(String castVote) {
        this.castVote = castVote;
    }

}
