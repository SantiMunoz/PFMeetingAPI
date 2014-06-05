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

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.planfeed.services.GoogleServiceImpl;


@Path("/googlerest")
public class GoogleREST {
	
	GoogleServiceImpl gs= new GoogleServiceImpl();
	private final static Logger LOGGER = Logger.getLogger(GoogleREST.class.getName()); 
	
	
	@POST
	@Path("/notifications")
	public Response reciveNotifications(
			@HeaderParam("X-Goog-Resource-ID") String resourceId,
			@HeaderParam("X-Goog-Resource-URI") String resourceUri,
			@HeaderParam("X-Goog-Channel-ID") String channelId,
			@HeaderParam("X-Goog-Channel-Expiration") String channelExpiration){
		
		try{
			LOGGER.info(resourceUri);
			gs.newNotification(resourceUri);
			return Response.ok().build();
		}catch(Exception e){
			LOGGER.severe("Error processing notification");
			throw new WebApplicationException(e.getStackTrace().toString());
		}
		
	}
	
	@POST
	@Path("/token/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response storeToken(@PathParam("email") String email, String code){
		try{
			gs.storeToken(email, code);
			return Response.ok().build();
		}catch(Exception e){
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}
	
	@POST
	@Path("/channelid")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response checkChannel (String channelID){
		try{
			return Response.ok(gs.checkChannelId(channelID)).build();
		}catch(Exception e){
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}


}
