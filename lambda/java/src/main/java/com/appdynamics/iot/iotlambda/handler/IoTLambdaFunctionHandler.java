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
import com.appdynamics.iot.iotlambda.model.IoTButtonRequest2;
import com.appdynamics.iot.iotlambda.model.IoTEnterpriseButtonRequest;
import com.appdynamics.iot.iotlambda.model.IoTButtonVote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.lang.Thread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.*;
import java.util.*;
import java.util.Map.Entry;

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
    	//IoTEnterpriseButtonRequest request = gson.fromJson(str, IoTEnterpriseButtonRequest.class);
    	IoTButtonRequest2 request = gson.fromJson(str, IoTButtonRequest2.class);
    	
    	AgentConfiguration agentConfig = IoTSDKHelper.ConfigureAgent();
        //DeviceInfo deviceInfo = IoTSDKHelper.ConfigureDevice("AWS IoT", "AWS IoT Button", request.getDeviceInfo().getDeviceId());
    	DeviceInfo deviceInfo = IoTSDKHelper.ConfigureDevice("AWS IoT", "AWS IoT Button", request.getSerialNumber());
        VersionInfo versionInfo = IoTSDKHelper.ConfigureDeviceVersion("1.0", "1.0", "1.0", "1.0");
    	
        Instrumentation.start(agentConfig, deviceInfo, versionInfo);
        
        //this.AddInstrumentationEvent(request.getDeviceEvent().getButtonClicked().getClickType() + "CLICK", request.getDeviceEvent().getButtonClicked().getClickType(), System.currentTimeMillis(), System.currentTimeMillis() + delayInt, Optional.empty());
        this.AddInstrumentationEvent(request.getClickType() + "CLICK", request.getClickType(), System.currentTimeMillis(), System.currentTimeMillis() + delayInt, Optional.empty());
        
        long voteCastEventStart = System.currentTimeMillis();    	       
    	
    	LambdaLogger logger = context.getLogger();  
    	
    	logger.log("Input: " + str);
        this.InitDB();
        
        IoTButtonVote vote = new IoTButtonVote();
        //vote.setButtonId(request.getDeviceInfo().getDeviceId());
        //vote.setVoteRecipient(request.getPlacementInfo().getAttributes().getVote());
        vote.setButtonId(request.getSerialNumber());
        vote.setVoteRecipient(request.getVoteRecipient());
        vote.setVoteBoard(request.getBoardNumber());
        
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
        
        logger.log("Vote Recipient: " + vote.getVoteRecipient());
        logger.log("Vote Board #: " + vote.getVoteBoard());
        
        HashMap<String, Object> voteCast = new HashMap<String, Object>();
        voteCast.put("VoteRecipient", vote.getVoteRecipient());
        voteCast.put("VoteBoard", vote.getVoteBoard());
        
        // Not "counting" any of Dali's votes. 
        if (vote.getVoteRecipient() == "Dali Rajic") {        	
        	voteCast.put("CountTheVote", false);
        } else {
        	voteCast.put("CountTheVote", true);
        }

        try {
        	Date d = Date.from(Instant.parse(vote.getVoteDatetime()));
            voteCast.put("VoteDatetime", d);
        } catch (Exception pe) {
        	Instrumentation.addErrorEvent(pe, Instrumentation.Severity.ALERT);
        }
        
        
        this.AddInstrumentationEvent("Vote Cast", "Vote Cast", voteCastEventStart, voteCastEventEnd, Optional.of(voteCast));              
        
        Instrumentation.sendAllEvents();
        
        outputStream.write(retval.getBytes(Charset.forName("UTF-8")));
    }
    
    private void AddInstrumentationEvent(String eventName, String eventDesc, long startTime, long duration, Optional<HashMap<String, Object>> props) {
    	CustomEvent.Builder eventBuilder = CustomEvent.builder(eventName, eventDesc).withTimestamp(startTime).withDuration(duration);
    	
    	HashMap<String, Object> properties = props.isPresent() ? props.get() : null;
    	
    	if (properties != null) {
    		Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
    		while (it.hasNext())
    		{
    			Entry<String, Object> entry = it.next();
    			if (entry.getValue() instanceof Date) {
    				eventBuilder.addDateProperty(entry.getKey(), (Date) entry.getValue());    				
    			}
    			
    			if (entry.getValue() instanceof Integer || entry.getValue() instanceof Long) {
    				eventBuilder.addLongProperty(entry.getKey(), (long) entry.getValue());
    			}
    			
    			if (entry.getValue() instanceof String) {
    				eventBuilder.addStringProperty(entry.getKey(), entry.getValue().toString());
    			}
    			
    			if (entry.getValue() instanceof Boolean) {
    				eventBuilder.addBooleanProperty(entry.getKey(), (boolean) entry.getValue());
    			}
    			
    			if (entry.getValue() instanceof Double) {
    				eventBuilder.addDoubleProperty(entry.getKey(), (double) entry.getValue());
    			}
    		}
    	}
    	
    	CustomEvent event = eventBuilder.build();
    	
    	Instrumentation.addEvent(event);
    }	    
    
    /*
     * Inits the AWS DB instance
     */
    private void InitDB() {
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
    	this.db = new DynamoDB(client);
    	this.table = db.getTable(DYNAMODB_TBL_NAME);
    }        
    
    

}
