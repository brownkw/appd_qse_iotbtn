package com.appdynamics.iot.iotlambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import com.appdynamics.iot.iotlambda.model.IoTButtonRequest;
import com.appdynamics.iot.iotlambda.model.IoTButtonVote;

@SuppressWarnings("unused")
public class IoTLambdaFunctionHandler implements RequestHandler<IoTButtonRequest, String> {

	private final Regions REGION = Regions.US_WEST_2;
	private DynamoDB db;
	private Table table;
	private final String DYNAMODB_TBL_NAME = "wbrown_qsr_0818";
	
    @Override
    public String handleRequest(IoTButtonRequest input, Context context)  {
        LambdaLogger logger = context.getLogger();       
        
        logger.log("Input: " + input);
        this.InitDB();
        
        IoTButtonVote vote = new IoTButtonVote();
        vote.setButtonId(input.getSerialNumber());
        vote.setVoteRecipient("Candidate01");
        
        try {
        	Item dbItem = new Item().withPrimaryKey("VoteRecipient", vote.getVoteRecipient(), "VoteDatetime", vote.getVoteDatetime()).with("Id", vote.getId()).with("ButtonId", vote.getButtonId());
        	PutItemOutcome outcome = table.putItem(dbItem);
        	
        	return outcome.getPutItemResult().toString();
        } catch (Exception e) {
        	logger.log("Could not put item: " + e.getMessage());
        	return e.getMessage();
        }
        
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
