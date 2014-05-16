package com.planfeed.others;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.calendar.Calendar;
import com.planfeed.bbdd.MySqlImpl;
import com.planfeed.bbdd.interfaces.Querys;
import com.planfeed.elements.Token;

public class GeneralMethods {
	GlobalValues globalVal =GlobalValues.getInstance();
	Calendar client;
	Querys bbdd = new MySqlImpl();
	
	Token token=null;
	GoogleCredential credentials=null;
	
	
	
	public GeneralMethods() {
		// TODO Auto-generated constructor stub
	}
	public Calendar getClient(String creatorEmail) throws Exception{
		token=bbdd.getTokenByEmail(creatorEmail);
		
		credentials = new GoogleCredential.Builder()
	    .setClientSecrets(globalVal.getClientId(), globalVal.getClientSecret())
	    .setJsonFactory(globalVal.getJSON_FACTORY()).setTransport(globalVal.getHttpTransport()).build()
	    .setRefreshToken(token.getResfreshToken()).setAccessToken(token.getToken());

		//credentials.refreshToken();

		client = new Calendar.Builder(globalVal.getHttpTransport(), globalVal.getJSON_FACTORY(), credentials).setApplicationName("PFMeeting").build();
		return client;
	}
	
	public String filterDescription(String description, String meetingId){
		try{
			String delims=globalVal.getMessageDescription(meetingId);
			String[] rs=description.split(delims);
			
			String result ="";
			if(rs.length>0) result= rs[0];
			if(rs.length>1)result+=rs[1];
			
			delims="[\r\n\\s]+";
			String[] auxRs = result.split(delims);
			String auxResult="";
			for(int i=0;i<auxRs.length;i++){
				auxResult+=auxRs[i];
			}
			if(auxResult.equals("")) result=auxResult;
			return result;
		}catch(Exception e){
			return description;
		}
		
	}
}
