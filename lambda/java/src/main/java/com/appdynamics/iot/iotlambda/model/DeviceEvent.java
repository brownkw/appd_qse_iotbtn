
package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceEvent {

    @SerializedName("buttonClicked")
    @Expose
    private ButtonClicked buttonClicked;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeviceEvent() {
    }

    /**
     * 
     * @param buttonClicked
     */
    public DeviceEvent(ButtonClicked buttonClicked) {
        super();
        this.buttonClicked = buttonClicked;
    }

    public ButtonClicked getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

}
