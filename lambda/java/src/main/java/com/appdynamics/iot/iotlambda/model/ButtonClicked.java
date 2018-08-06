
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ButtonClicked {

    @SerializedName("clickType")
    @Expose
    private String clickType;
    @SerializedName("reportedTime")
    @Expose
    private String reportedTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ButtonClicked() {
    }

    /**
     * 
     * @param reportedTime
     * @param clickType
     */
    public ButtonClicked(String clickType, String reportedTime) {
        super();
        this.clickType = clickType;
        this.reportedTime = reportedTime;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(String reportedTime) {
        this.reportedTime = reportedTime;
    }

}
