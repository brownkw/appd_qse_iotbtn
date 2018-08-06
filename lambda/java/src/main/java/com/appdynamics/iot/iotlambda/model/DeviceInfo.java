
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceInfo {

    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("remainingLife")
    @Expose
    private Double remainingLife;
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeviceInfo() {
    }

    /**
     * 
     * @param remainingLife
     * @param attributes
     * @param type
     * @param deviceId
     */
    public DeviceInfo(String deviceId, String type, Double remainingLife, Attributes attributes) {
        super();
        this.deviceId = deviceId;
        this.type = type;
        this.remainingLife = remainingLife;
        this.attributes = attributes;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRemainingLife() {
        return remainingLife;
    }

    public void setRemainingLife(Double remainingLife) {
        this.remainingLife = remainingLife;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
