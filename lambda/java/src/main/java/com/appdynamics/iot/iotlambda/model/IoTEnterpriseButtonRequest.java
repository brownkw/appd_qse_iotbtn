
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IoTEnterpriseButtonRequest {

    @SerializedName("deviceInfo")
    @Expose
    private DeviceInfo deviceInfo;
    @SerializedName("deviceEvent")
    @Expose
    private DeviceEvent deviceEvent;
    @SerializedName("placementInfo")
    @Expose
    private PlacementInfo placementInfo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IoTEnterpriseButtonRequest() {
    }

    /**
     * 
     * @param deviceEvent
     * @param deviceInfo
     * @param placementInfo
     */
    public IoTEnterpriseButtonRequest(DeviceInfo deviceInfo, DeviceEvent deviceEvent, PlacementInfo placementInfo) {
        super();
        this.deviceInfo = deviceInfo;
        this.deviceEvent = deviceEvent;
        this.placementInfo = placementInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DeviceEvent getDeviceEvent() {
        return deviceEvent;
    }

    public void setDeviceEvent(DeviceEvent deviceEvent) {
        this.deviceEvent = deviceEvent;
    }

    public PlacementInfo getPlacementInfo() {
        return placementInfo;
    }

    public void setPlacementInfo(PlacementInfo placementInfo) {
        this.placementInfo = placementInfo;
    }

}
