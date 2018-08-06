/**
 * 
 */
package com.appdynamics.iot.iotlambda.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author wayne.brown
 * Represents the elements for registering a vote in DynamoDB
 */
public class IoTButtonVote {
		
	private String Id;
	private String ButtonId;
	private String VoteRecipient;
	private String VoteDatetime;
	
	/**
	 * Creates an instance of the IoTButtonVote object
	 */
	public IoTButtonVote() {
		this.setId();
		this.setVoteDatetime();
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}
	/**
	 * Sets the id
	 */
	private void setId() {
		Id = UUID.randomUUID().toString();
	}
	
	/**
	 * @return the buttonId
	 */
	public String getButtonId() {
		return ButtonId;
	}
	/**
	 * @param buttonId the buttonId to set
	 */
	public void setButtonId(String buttonId) {
		ButtonId = buttonId;
	}
	/**
	 * @return the voteRecipient
	 */
	public String getVoteRecipient() {
		return VoteRecipient;
	}
	/**
	 * @param voteRecipient the voteRecipient to set
	 */
	public void setVoteRecipient(String voteRecipient) {
		VoteRecipient = voteRecipient;
	}
	/**
	 * @return the voteDatetime
	 */
	public String getVoteDatetime() {
		return VoteDatetime;
	}
	/**
	 * Sets the voteDatetime
	 */
	private void setVoteDatetime() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		VoteDatetime = df.format(new Date());
	}	
}
