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
package com.planfeed.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


@SuppressWarnings("serial")
public class MeetingNotFoundException extends WebApplicationException {
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  */
	  public MeetingNotFoundException() {
	    super(Response.status(Response.Status.NOT_FOUND).build());
	  }
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  * @param message the String that is the entity of the 404 response.
	  */
	  public MeetingNotFoundException(String message) {
		  super(Response.status(Response.Status.NOT_FOUND).entity(message).type("text/plain").build());
	  }
	}
