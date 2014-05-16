package com.planfeed.others;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GlobalValues {


	private static GlobalValues uniqueInstance;
	/** Global instance of the HTTP transport. */
	  private static HttpTransport HTTP_TRANSPORT;

	  /** Global instance of the JSON factory. */
	  private static JsonFactory JSON_FACTORY;
	  
	  private static final String CLIENT_ID = "XXX";
		private static final String CLIENT_SECRET = "XXX";
		private static final String MESSAGE_DESCRIPTION="\nMeeting in Plan&Feedback Meeting Tool:\nhttp://pfmeeting.com/#/meeting/";
	
	private GlobalValues(){}

	public static GlobalValues getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new GlobalValues();
			HTTP_TRANSPORT= new NetHttpTransport();
			JSON_FACTORY=JacksonFactory.getDefaultInstance();
		}
		return uniqueInstance;
	}
	
	public HttpTransport getHttpTransport(){
		return HTTP_TRANSPORT;
	}

	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	public  String getClientSecret() {
		return CLIENT_SECRET;
	}

	public  String getClientId() {
		return CLIENT_ID;
	}
	public String getMessageDescription(String meetingId){
		return MESSAGE_DESCRIPTION+meetingId;
	}

}
