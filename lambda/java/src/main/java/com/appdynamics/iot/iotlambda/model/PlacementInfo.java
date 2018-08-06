
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlacementInfo {

    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("placementName")
    @Expose
    private String placementName;
    @SerializedName("attributes")
    @Expose
    private Attributes_ attributes;
    @SerializedName("devices")
    @Expose
    private Devices devices;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlacementInfo() {
    }

    /**
     * 
     * @param placementName
     * @param attributes
     * @param devices
     * @param projectName
     */
    public PlacementInfo(String projectName, String placementName, Attributes_ attributes, Devices devices) {
        super();
        this.projectName = projectName;
        this.placementName = placementName;
        this.attributes = attributes;
        this.devices = devices;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPlacementName() {
        return placementName;
    }

    public void setPlacementName(String placementName) {
        this.placementName = placementName;
    }

    public Attributes_ getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes_ attributes) {
        this.attributes = attributes;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

}
