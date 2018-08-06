
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {

    @SerializedName("projectRegion")
    @Expose
    private String projectRegion;
    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("placementName")
    @Expose
    private String placementName;
    @SerializedName("deviceTemplateName")
    @Expose
    private String deviceTemplateName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attributes() {
    }

    /**
     * 
     * @param deviceTemplateName
     * @param placementName
     * @param projectRegion
     * @param projectName
     */
    public Attributes(String projectRegion, String projectName, String placementName, String deviceTemplateName) {
        super();
        this.projectRegion = projectRegion;
        this.projectName = projectName;
        this.placementName = placementName;
        this.deviceTemplateName = deviceTemplateName;
    }

    public String getProjectRegion() {
        return projectRegion;
    }

    public void setProjectRegion(String projectRegion) {
        this.projectRegion = projectRegion;
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

    public String getDeviceTemplateName() {
        return deviceTemplateName;
    }

    public void setDeviceTemplateName(String deviceTemplateName) {
        this.deviceTemplateName = deviceTemplateName;
    }

}
