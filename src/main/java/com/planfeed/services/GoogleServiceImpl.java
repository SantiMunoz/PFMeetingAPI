/*Copyright 2014 Santi Muñoz Mallofre
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.planfeed.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.planfeed.bbdd.MySqlImpl;
import com.planfeed.bbdd.interfaces.Querys;
import com.planfeed.elements.Meeting;
import com.planfeed.elements.Token;
import com.planfeed.others.GeneralMethods;
import com.planfeed.others.GlobalValues;
import com.planfeed.services.exceptions.MeetingNotFound;
import com.planfeed.services.interfaces.MeetingService;

public class GoogleServiceImpl {
	
	
	GlobalValues globalVal =GlobalValues.getInstance();
	Calendar client;
	GeneralMethods generalMeth =new GeneralMethods();
	Querys bbdd = new MySqlImpl();
	MeetingService meetingService = new MeetingServiceImpl();
	private final static Logger LOGGER = Logger.getLogger(GoogleServiceImpl.class.getName()); 
	 
	public GoogleServiceImpl() {}

	
	public void storeToken (String email, String code)throws Exception{
		try{
			// Actualiza el código de autorización a un acceso y a un token de actualización.
		    GoogleTokenResponse tokenResponse =
		        new GoogleAuthorizationCodeTokenRequest(globalVal.getHttpTransport(), globalVal.getJSON_FACTORY(),
		        		globalVal.getClientId(), globalVal.getClientSecret(), code, "postmessage").execute();
		    
		   
		    
		    if(tokenResponse.getRefreshToken()!=null){
		    	Token tok = new Token();
			    tok.setEmail(email);
			    tok.setToken(tokenResponse.getAccessToken());
			    tok.setResfreshToken(tokenResponse.getRefreshToken());
			    try{
			    	bbdd.putToken(tok);
			    }catch(Exception e){
			    	
			    }
		    }
		    
		    
		    
		    // Crea una representación credencial de los datos del token.
		    GoogleCredential credential = new GoogleCredential.Builder()
		        .setJsonFactory(globalVal.getJSON_FACTORY())
		        .setTransport(globalVal.getHttpTransport())
		        .setClientSecrets(globalVal.getClientId(), globalVal.getClientSecret()).build()
		        .setFromTokenResponse(tokenResponse);


		}catch (TokenResponseException e) {
		   // response.status(500);
		   // return GSON.toJson("Failed to upgrade the authorization code.");
		} catch (IOException e) {
		    throw new Exception(e.getStackTrace().toString());
		}
	}
	
	public void newNotification(String resourceUri) throws Exception{
		ArrayList<Meeting> meetingsList = new ArrayList<Meeting>();
		Token token=null;
		String calendarId;

		boolean trobat=false;
		GoogleCredential credentials=null;
		Calendar client=null;

		calendarId = getCalendarId(resourceUri);
		
		try{
			try{
				meetingsList=bbdd.getMeetingsbyCalendarId(calendarId);
				LOGGER.info(meetingsList.size()+" meetings for this calendar: "+calendarId);
			}catch(MeetingNotFound e){
				//no meetings, do nothing
				LOGGER.info("No meetings for this calendar: "+calendarId);
			}catch(Exception e){
				throw new Exception(e.getStackTrace().toString());
			}
			
			boolean first=true;
			boolean anyChanges;
			
			Iterator<Meeting> it = meetingsList.iterator();
			while(it.hasNext()&& !trobat){
				Meeting auxMeeting = it.next();
				anyChanges=false;
				if(first){
					
					client=generalMeth.getClient(auxMeeting.getCreatorEmail());
					
				}else{
					first=false;
				}
				
				Event result = client.events().get(auxMeeting.getCalendarId(), auxMeeting.getCalendarEventId()).execute();
				
				if(!auxMeeting.getTitle().equalsIgnoreCase(result.getSummary())){
					anyChanges=true;
					auxMeeting.setTitle(result.getSummary());
				}
				
				String auxDescription = generalMeth.filterDescription(result.getDescription(), auxMeeting.getMeetingId());
				if(!auxMeeting.getDescription().equalsIgnoreCase(auxDescription)){
					anyChanges=true;
					auxMeeting.setDescription(auxDescription);
				}
				
				if(auxMeeting.getDate()!=result.getStart().getDateTime().getValue()){
					anyChanges=true;
					auxMeeting.setDate(result.getStart().getDateTime().getValue());
				}
				
				if(anyChanges){
					bbdd.putMeetingOnly(auxMeeting);
				}
			}

			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getStackTrace().toString());
		}

	}
	
	public String getCalendarId(String resourceUri){
		String delims="https://content.googleapis.com/calendar/v3/calendars/";
		String[] rs=resourceUri.split(delims);
		delims="/";
		rs=rs[1].split(delims);
		
		return rs[0];
	}
	
	

}
