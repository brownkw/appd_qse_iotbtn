/**
 * 
 */
package com.appdynamics.iot.iotlambda.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wayne.brown
 * Represents the data sent by the AWS IoT button
 */
public class IoTButtonRequest {
	private String serialNumber;
    private String batteryVoltage;
    private String clickType;

    /**
     * Gets the serial number of the IoT button that sent the request
     * @return the AWS IoT button serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number for the IoT button request 
     * @param serialNumber the AWS IoT button serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets the battery voltage of the IoT button that sent the request
     * @return the AWS IoT button battery voltage
     */
    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     * Sets the battery voltage for the IoT button request
     * @param batteryVoltage the AWS IoT button battery voltage
     */
    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    /**
     * Gets the click type from the IoT button request
     * @return the AWS IoT button click type (single, double, long)
     */
    public String getClickType() {
        return clickType;
    }

    /**
     * Sets the click type for the IoT button request
     * @param clickType the AWS IoT button click type 
     */
    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
        try {
        	String requestStr = mapper.writeValueAsString(this);        	
        	return requestStr;
        } catch (JsonProcessingException e) {
        	return e.toString();
        }
	}
    
    
}
