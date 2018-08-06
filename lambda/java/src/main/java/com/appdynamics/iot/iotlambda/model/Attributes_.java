
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes_ {

    @SerializedName("Room")
    @Expose
    private String room;
    @SerializedName("Vote")
    @Expose
    private String vote;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attributes_() {
    }

    /**
     * 
     * @param vote
     * @param room
     */
    public Attributes_(String room, String vote) {
        super();
        this.room = room;
        this.vote = vote;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
