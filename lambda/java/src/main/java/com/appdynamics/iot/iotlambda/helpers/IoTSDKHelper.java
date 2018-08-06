/**
 * 
 */
package com.appdynamics.iot.iotlambda.helpers;

import java.util.Properties;
import java.util.ResourceBundle;
import java.io.InputStream;
import java.io.IOException;

import com.appdynamics.iot.AgentConfiguration;
import com.appdynamics.iot.DeviceInfo;
import com.appdynamics.iot.VersionInfo;

/**
 * @author wayne.brown
 *
 */
public class IoTSDKHelper {
	
	/**
	 * Configures the IoT SDK Agent
	 * @return the AgentConfiguration object 
	 */
	public static AgentConfiguration ConfigureAgent() throws IOException {			
		
		final String APP_KEY = "AD-AAB-AAK-YBY";
		final String APP_COLLECTOR_URL = "https://iot-col.eum-appdynamics.com";
		
		AgentConfiguration.Builder builder = AgentConfiguration.builder();
		AgentConfiguration config = builder.withAppKey(APP_KEY).withCollectorUrl(APP_COLLECTOR_URL).build();
		
		return config;
		
	}
	
	/**
	 * Configures the IoT device
	 * @param device_type device type
	 * @param device_name device name
	 * @param device_id device ID
	 * @return the DeviceInfo object
	 */
	public static DeviceInfo ConfigureDevice(String device_type, String device_name, String device_id) {
		DeviceInfo.Builder builder = DeviceInfo.builder(device_type, device_id);
		DeviceInfo info = builder.withDeviceName(device_name).build();
		
		return info;
	}
	
	/**
	 * Configures the IoT version info
	 * @param firmware_version firmware version
	 * @param hardware_version hardware version 
	 * @param os_version OS version
	 * @param software_version software version
	 * @return the VersionInfo object
	 */
	public static VersionInfo ConfigureDeviceVersion(String firmware_version, String hardware_version, String os_version, String software_version) {
		VersionInfo.Builder builder = VersionInfo.builder();		
		VersionInfo info = builder.withFirmwareVersion(firmware_version).withHardwareVersion(hardware_version).withOsVersion(os_version).withSoftwareVersion(software_version).build();
		
		return info;
	}
}
