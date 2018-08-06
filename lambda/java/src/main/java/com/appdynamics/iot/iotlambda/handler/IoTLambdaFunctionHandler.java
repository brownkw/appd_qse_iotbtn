package com.appdynamics.iot.iotlambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import com.appdynamics.iot.iotlambda.helpers.IoTSDKHelper;

import com.appdynamics.iot.iotlambda.model.IoTButtonRequest;
import com.appdynamics.iot.iotlambda.model.IoTEnterpriseButtonRequest;
import com.appdynamics.iot.iotlambda.model.IoTButtonVote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.io.IOException;
import java.lang.Thread;
import java.util.Random;

import com.appdynamics.iot.Instrumentation;
import com.appdynamics.iot.AgentConfiguration;
import com.appdynamics.iot.DeviceInfo;
import com.appdynamics.iot.VersionInfo;
import com.appdynamics.iot.events.CustomEvent;

@SuppressWarnings("unused")
public class IoTLambdaFunctionHandler implements RequestStreamHandler {

	private final Regions REGION = Regions.US_WEST_2;
	private DynamoDB db;
	private Table table;
	private final String DYNAMODB_TBL_NAME = "wbrown_qsr_0818";	
	
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    	
    	// Add randomness
        int delayInt = 50;
        Random rand = new Random();
        delayInt += rand.nextInt(1500);
        
    	// Read the input stream and cast to IoTEnterpriseButtonRequest object
    	String str = IOUtils.toString(inputStream, "UTF-8");    	
    	Gson gson = new GsonBuilder().create();
    	IoTEnterpriseButtonRequest request = gson.fromJson(str, IoTEnterpriseButtonRequest.class);
    	
    	AgentConfiguration agentConfig = IoTSDKHelper.ConfigureAgent();
        DeviceInfo deviceInfo = IoTSDKHelper.ConfigureDevice("AWS IoT Button Enterprise", "AWS IoT Button Enterprise", request.getDeviceInfo().getDeviceId());
        VersionInfo versionInfo = IoTSDKHelper.ConfigureDeviceVersion("1.0", "1.0", "1.0", "1.0");
    	
        Instrumentation.start(agentConfig, deviceInfo, versionInfo);
        
        this.AddInstrumentationEvent(request.getDeviceEvent().getButtonClicked().getClickType() + "CLICK", request.getDeviceEvent().getButtonClicked().getClickType(), System.currentTimeMillis(), System.currentTimeMillis() + delayInt);
        
        long voteCastEventStart = System.currentTimeMillis();    	       
    	
    	LambdaLogger logger = context.getLogger();  
    	
    	logger.log("Input: " + str);
        this.InitDB();
        
        IoTButtonVote vote = new IoTButtonVote();
        vote.setButtonId(request.getDeviceInfo().getDeviceId());
        vote.setVoteRecipient(request.getPlacementInfo().getAttributes().getVote());
        
        String retval = "";
        
        try {
        	Item dbItem = new Item().withPrimaryKey("VoteRecipient", vote.getVoteRecipient(), "VoteDatetime", vote.getVoteDatetime()).with("Id", vote.getId()).with("ButtonId", vote.getButtonId()).with("Str", str);
        	PutItemOutcome outcome = table.putItem(dbItem);
        	
        	retval = outcome.getPutItemResult().toString();
        } catch (Exception e) {
        	logger.log("Could not put item: " + e.getMessage());
        	retval = e.getMessage();
        	Instrumentation.addErrorEvent(e, Instrumentation.Severity.ALERT);
        }
        
        long voteCastEventEnd = System.currentTimeMillis() + delayInt + 1000;              
        
        AddInstrumentationEvent("Vote Cast", "Vote Cast", voteCastEventStart, voteCastEventEnd);              
        
        Instrumentation.sendAllEvents();
        
        outputStream.write(retval.getBytes(Charset.forName("UTF-8")));
    }
    
    private void AddInstrumentationEvent(String eventName, String eventDesc, long startTime, long duration) {
    	CustomEvent.Builder eventBuilder = CustomEvent.builder(eventName, eventDesc);
    	CustomEvent event = eventBuilder.withTimestamp(startTime).withDuration(duration).build();
    	
    	Instrumentation.addEvent(event);
    }
	
    /*@Override
    public String handleRequest(Object input, Context context)  {
        AgentConfiguration agentConfig = IoTSDKHelper.ConfigureAgent();
        DeviceInfo deviceInfo = IoTSDKHelper.ConfigureDevice("AWS IoT Button", "AWS IoT Button (Regular)", "12345");
        VersionInfo versionInfo = IoTSDKHelper.ConfigureDeviceVersion("1.0", "1.0", "1.0", "1.0");
    	
        Instrumentation.start(agentConfig, deviceInfo, versionInfo);
        
        CustomEvent.Builder voteCastEvent = CustomEvent.builder("Vote Cast", "Vote cast");
        long voteCastEventStart = System.currentTimeMillis();
        
    	LambdaLogger logger = context.getLogger();       	
        
        //logger.log("Input: " + input.toString());
        this.InitDB();
        
        IoTButtonVote vote = new IoTButtonVote();
        vote.setButtonId("12345");
        vote.setVoteRecipient("Candidate01");
        
        String retval = "";
        
        try {
        	Item dbItem = new Item().withPrimaryKey("VoteRecipient", vote.getVoteRecipient(), "VoteDatetime", vote.getVoteDatetime()).with("Id", vote.getId()).with("ButtonId", vote.getButtonId()).with("Str", input.toString());
        	PutItemOutcome outcome = table.putItem(dbItem);
        	
        	retval = outcome.getPutItemResult().toString();
        } catch (Exception e) {
        	logger.log("Could not put item: " + e.getMessage());
        	retval = e.getMessage();
        }
        
        return retval;
        
    }*/
    
    /*
     * Inits the AWS DB instance
     */
    private void InitDB() {
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
    	this.db = new DynamoDB(client);
    	this.table = db.getTable(DYNAMODB_TBL_NAME);
    }        
    
    

}
