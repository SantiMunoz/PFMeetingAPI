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
 
package com.planfeed.api;



import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.planfeed.api.exceptions.MeetingNotFoundException;
import com.planfeed.elements.Meeting;
import com.planfeed.services.MeetingServiceImpl;
import com.planfeed.services.exceptions.MeetingNotFound;
import com.planfeed.services.interfaces.MeetingService;

@Path("/meetings")
public class MeetingREST {

	
	MeetingService meetingService = new MeetingServiceImpl();
	
	@GET
	@Path("/{meetingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeeting(@PathParam("meetingId") String meetingId){
		try{
			return Response.ok(meetingService.getMeeting(meetingId)).build();
		}catch(MeetingNotFound ex){
			throw new MeetingNotFoundException("Item, " + meetingId + ", is not found");
		}catch(Exception e){
			throw new WebApplicationException(e.getStackTrace().toString());
		}
		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMeeting(Meeting meeting,@DefaultValue("false") @QueryParam("updateEventCalendar") boolean updateEventCalendar ) {
		try{
			
			return Response.ok(meetingService.putMeeting(meeting, updateEventCalendar)).build();
		}catch(MeetingNotFound ex){
			throw new MeetingNotFoundException("Item, " + meeting.getMeetingId() + ", is not found");
		}catch(Exception e){
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}
	


	@PUT
	@Path("/{meetingId}/status")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response putStatus(@PathParam("meetingId") String meetingId, String newStatus) {
		try{
			
			meetingService.modifyStatus(meetingId, newStatus);
			return Response.ok().build();
		}catch(MeetingNotFound ex){
			throw new MeetingNotFoundException("Item, " + meetingId + ", is not found");
		}catch(Exception e){
			throw new WebApplicationException(e.getStackTrace().toString());
		}

	}

	@DELETE
	@Path("/{meetingId}")
	public Response deleteMeeting(@PathParam("meetingId") String meetingId) {

			throw new WebApplicationException("Method not implemented");
		
	}
	
	@GET
	@Path("/{meetingId}/acta")
	@Produces("application/pdf")
	public Response getActa(@PathParam("meetingId") String meetingId){
		try{
			
			Response response = Response
		            .ok()
		            .type("application/pdf")
		            .entity(meetingService.getActa(meetingId).toByteArray())
		            .build();
		    return response;
		}catch(MeetingNotFound ex){
			throw new MeetingNotFoundException("Item, " + meetingId + ", is not found");
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException("Fail to create pdf");
		}

	}

}
