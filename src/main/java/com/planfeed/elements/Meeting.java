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
package com.planfeed.elements;

import java.util.ArrayList;


//@XmlRootElement
public class Meeting {

	private String meetingId = null;
	private String title= "";
	private long date=0l;
	private String description="";
	private ArrayList<PointOfAgenda> agenda = new ArrayList<PointOfAgenda>();
	private long init=0l;
	private long pauseDate = 0l;
	private String status=null;
	private long initOffTime = 0l;
	private String creatorEmail=null;
	private String calendarEventId=null;
	private String calendarId=null;
	private long timeFinish=0l;
	
	public Meeting() {
		
	}

	public Meeting(String meetingId, String title, long date,
			String description, ArrayList<PointOfAgenda> agenda, long init,
			long pauseDate, String status, long initOffTime) {
		super();
		this.meetingId = meetingId;
		this.title = title;
		this.date = date;
		this.description = description;
		this.agenda = agenda;
		this.init = init;
		this.pauseDate = pauseDate;
		this.status = status;
		this.initOffTime=initOffTime;
	}


	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<PointOfAgenda> getAgenda() {
		return agenda;
	}
	public void setAgenda(ArrayList<PointOfAgenda> agenda) {
		this.agenda = agenda;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getInit() {
		return init;
	}

	public void setInit(long init) {
		this.init = init;
	}
	public long getPauseDate() {
		return pauseDate;
	}
	public void setPauseDate(long pauseDate) {
		this.pauseDate = pauseDate;
	}

	public long getInitOffTime() {
		return initOffTime;
	}

	public void setInitOffTime(long initOffTime) {
		this.initOffTime = initOffTime;
	}


	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getCalendarEventId() {
		return calendarEventId;
	}

	public void setCalendarEventId(String calendarEventId) {
		this.calendarEventId = calendarEventId;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public long getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(long timeFinish) {
		this.timeFinish = timeFinish;
	}





}
