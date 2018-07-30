/**
 * This is a sample Lambda function that sends an SMS on click of a
 * button. It needs one permission sns:Publish. The following policy
 * allows SNS publish to SMS but not topics or endpoints.
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "sns:Publish"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Deny",
            "Action": [
                "sns:Publish"
            ],
            "Resource": [
                "arn:aws:sns:*:*:*"
            ]
        }
    ]
}
 *
 * The following JSON template shows what is sent as the payload:
{
    "serialNumber": "GXXXXXXXXXXXXXXXXX",
    "batteryVoltage": "xxmV",
    "clickType": "SINGLE" | "DOUBLE" | "LONG"
}
 *
 * A "LONG" clickType is sent if the first press lasts longer than 1.5 seconds.
 * "SINGLE" and "DOUBLE" clickType payloads are sent for short clicks.
 *
 * For more documentation, follow the link below.
 * http://docs.aws.amazon.com/iot/latest/developerguide/iot-lambda-rule.html
 */

'use strict';

const AWS = require('aws-sdk');
var documentClient = new AWS.DynamoDB.DocumentClient(); 

exports.handler = (event, context, callback) => {
    
    
    console.log('Received event:', event);
    console.log('Logging vote...');
    
    var params = {
		Item : {
			"Id" : Math.random().toString(36).slice(2),
			"ButtonId" : event.serialNumber,
			"VoteRecipient" : "Candidate01",
			"VoteDatetime" : new Date().toISOString()
		},
		TableName : "wbrown_qsr_0818"
    };
    
    documentClient.put(params, function(err, data){
		callback(err, data);
	});
};

/* const SNS = new AWS.SNS({ apiVersion: '2010-03-31' });
const PHONE_NUMBER = '1-704-787-4645'; // change it to your phone number

exports.handler = (event, context, callback) => {
    console.log('Received event:', event);

    console.log(`Sending SMS to ${PHONE_NUMBER}`);
    const payload = JSON.stringify(event);
    const params = {
        PhoneNumber: PHONE_NUMBER,
        Message: `Hello from your IoT Button ${event.serialNumber}. Here is the full event: ${payload}.`,
    };
    // result will go to function callback
    SNS.publish(params, callback);
}; */
