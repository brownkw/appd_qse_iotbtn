package com.appdynamics.iot.iotlambda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class IoTButtonRequest2 {

	@SerializedName("serialNumber")
	@Expose
	private String serialNumber;
	@SerializedName("batteryVoltage")
	@Expose
	private String batteryVoltage;
	@SerializedName("clickType")
	@Expose
	private String clickType;
	
	private final String[] DALI_BUTTONS = { "G030MD042106WXLB", "G030MD043224FR2H", "G030MD041166E2CL"};
	private final String[] DAVID_BUTTONS = { "G030MD044433P97H", "G030MD040036PKGK", "G030MD042127LFV1" };
	private final String[] TOML_BUTTONS = { "G030MD04845476VX", "G030MD043256SPF5", "G030MD0474726LW2" };
	private final String[] GHAZAL_BUTTONS = { "G030MD0484117N3L", "G030MD043016BQTE", "G030MD04842794N7" };
	private final String[] TOMS_BUTTONS = { "G030MD042353BGTW", "G030MD0485056MHP", "G030MD0483455L0N" };
	private final String[] JIM_BUTTONS = { "G030MD0450230347", "G030MD0462561VGF", "G030MD0451549RE3" };

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public IoTButtonRequest2() {
	}

	/**
	 * 
	 * @param serialNumber
	 * @param clickType
	 * @param batteryVoltage
	 */
	public IoTButtonRequest2(String serialNumber, String batteryVoltage, String clickType) {
		super();
		this.serialNumber = serialNumber;
		this.batteryVoltage = batteryVoltage;
		this.clickType = clickType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public String getClickType() {
		return clickType;
	}

	public void setClickType(String clickType) {
		this.clickType = clickType;
	}
	
	public String getBoardNumber() {
		String voteRecipient = this.getVoteRecipient();
		String serialNumber = this.getSerialNumber();
		
		long retval = -1;
		
		switch(voteRecipient) {
			case "Dali Rajic":
				retval = Arrays.asList(this.DALI_BUTTONS).indexOf(serialNumber) + 1;
				break;
			case "David Wadhwani":
				retval = Arrays.asList(this.DAVID_BUTTONS).indexOf(serialNumber) + 1;
				break;
			case "Tom Schmit":
				retval = Arrays.asList(this.TOMS_BUTTONS).indexOf(serialNumber) + 1;
				break;
			case "Tom Levey":
				retval = Arrays.asList(this.TOML_BUTTONS).indexOf(serialNumber) + 1;
				break;
			case "Ghazal Asif":
				retval = Arrays.asList(this.GHAZAL_BUTTONS).indexOf(serialNumber) + 1;
				break;
			case "Jim Cavanaugh":
				retval = Arrays.asList(this.JIM_BUTTONS).indexOf(serialNumber) + 1;
				break;
			default: 
				break;
		}
		
		return Long.toString(retval);
	}
	
	public String getVoteRecipient() {		
		
		String serialNumber = this.getSerialNumber();
		
		if (Arrays.asList(this.DALI_BUTTONS).contains(serialNumber))
		{
			return "Dali Rajic";
		}
		
		if (Arrays.asList(this.DAVID_BUTTONS).contains(serialNumber))
		{
			return "David Wadhwani";
		}
		
		if (Arrays.asList(this.TOMS_BUTTONS).contains(serialNumber))
		{
			return "Tom Schmit";
		}
		
		if (Arrays.asList(this.TOML_BUTTONS).contains(serialNumber))
		{
			return "Tom Levey";
		}
		
		if (Arrays.asList(this.GHAZAL_BUTTONS).contains(serialNumber))
		{
			return "Ghazal Asif";
		}
		
		if (Arrays.asList(this.JIM_BUTTONS).contains(serialNumber))
		{
			return "Jim Cavanaugh";
		}
		
		return "";
	}

}