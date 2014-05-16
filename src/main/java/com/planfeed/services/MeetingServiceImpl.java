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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.StreamingOutput;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.planfeed.bbdd.BbddMock;
import com.planfeed.bbdd.MySqlImpl;
import com.planfeed.bbdd.interfaces.Querys;
import com.planfeed.elements.Meeting;
import com.planfeed.elements.PointOfAgenda;
import com.planfeed.others.GeneralMethods;
import com.planfeed.others.GlobalValues;
import com.planfeed.others.HeaderFooter;
import com.planfeed.others.IdGenerator;
import com.planfeed.services.exceptions.MeetingNotFound;
import com.planfeed.services.interfaces.MeetingService;

public class MeetingServiceImpl implements MeetingService {

	//Querys bbdd = BbddMock.getInstance();
	Querys bbdd = new MySqlImpl();
	IdGenerator idGen=IdGenerator.getInstance();
	Calendar client;
	GeneralMethods generalMeth =new GeneralMethods();
	GlobalValues globalVal =GlobalValues.getInstance();
	final private static String ORDER_CHANGE_STATUS = "orderChange";
	
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 21,  Font.BOLD);
	private static Font titlePointFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,  Font.BOLD);
	private static Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	
	public MeetingServiceImpl(){};
	
	public Meeting getMeeting(String id) throws Exception {
		try{
			return bbdd.getMeeting(id);
		}catch (MeetingNotFound e){
			throw new MeetingNotFound();
		}catch (Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		
	}


	public Meeting putMeeting(Meeting meeting, boolean updateEventCalendar) throws Exception {
		Meeting meetingAux=null;
		boolean newMeet=false;
		if(meeting.getMeetingId()==null){
			
			if(meeting.getCalendarEventId()!=null){
				try{
					String meetingId = bbdd.getMeetingIdByEventId(meeting.getCalendarEventId());
					meetingAux=this.getMeeting(meetingId);
					return meetingAux;
				}catch(MeetingNotFound e){
					newMeet=true;
					String newId = idGen.generateId();
					meeting.setMeetingId(newId);
				}catch( Exception e){
					throw new Exception(e.getStackTrace().toString());
				}
			}else{
				newMeet=true;
				String newId = idGen.generateId();
				meeting.setMeetingId(newId);
			}
			
			

		}else{
			try{
				meetingAux=this.getMeeting(meeting.getMeetingId());
			}catch (MeetingNotFound e){
				throw new MeetingNotFound();
			}
		}
		for(PointOfAgenda point: meeting.getAgenda()){
			if(point.getPointId()==null){
				point.setPointId(idGen.generateId());
			}
		}
		
		
		
		try{
			if(!newMeet&&!meetingAux.getStatus().equalsIgnoreCase(meeting.getStatus())){
				meeting=modifyStatusImpl(meeting,meeting.getStatus());
			}
			bbdd.putMeeting(meeting);
			
			if(updateEventCalendar){
				client=generalMeth.getClient(meeting.getCreatorEmail());
				Event result = client.events().get(meeting.getCalendarId(), meeting.getCalendarEventId()).execute();
				result.setSummary(meeting.getTitle());
				
				DateTime auxDate = new DateTime(meeting.getDate());
				EventDateTime start = new EventDateTime();
				start.setDateTime(auxDate);
				result.setStart(start);
				
				long endMili = meeting.getDate()+calculateMeetingTime(meeting.getAgenda());
				auxDate = new DateTime(endMili);
				EventDateTime end = new EventDateTime();
				end.setDateTime(auxDate);
				result.setEnd(end);
				
				String auxDescription=meeting.getDescription()+globalVal.getMessageDescription(meeting.getMeetingId());
				result.setDescription(auxDescription);
				client.events().update(meeting.getCalendarId(), result.getId(), result).execute();
				
			}
			
			return meeting;
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e.getStackTrace().toString());
		}
		
	}





	public void modifyStatus(String meetingId, String newStatus)throws Exception {
		try{
			
			Meeting meeting = bbdd.getMeeting(meetingId);

				meeting=modifyStatusImpl(meeting,newStatus);
				
			
			bbdd.putMeeting(meeting);
		}catch (MeetingNotFound e){
			throw new MeetingNotFound();
		}catch (Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		
	}

	private Meeting modifyStatusImpl(Meeting meeting, String newStatus){
		
		if(meeting.getStatus()==null || !meeting.getStatus().equalsIgnoreCase(newStatus) || ORDER_CHANGE_STATUS.equalsIgnoreCase(newStatus)){

			switch(newStatus){
				case "play":
					meeting.setInit((new Date()).getTime());
					meeting.setStatus("play");
					break;
				case ORDER_CHANGE_STATUS:
					meeting.setInit((new Date()).getTime());
					break;
				case "pause":
					meeting.setPauseDate((new Date()).getTime());
					meeting.setStatus(newStatus);
					break;
				case "stop":
					meeting.setInit(0l);
					meeting.setPauseDate(0l);
					meeting.setStatus(newStatus);
					meeting.setInitOffTime(0l);
					break;
				case "finished":
					meeting.setStatus(newStatus);
					break;
				case "offTime":
					meeting.setStatus(newStatus);
					meeting.setInitOffTime((new Date()).getTime());
					break;
				default:
					//do nothing
					break;
				}
		}
		return meeting;
	}

	public void deleteMeeting(String meetingId) throws Exception {
		//not implemented
		
	}
	
	public ByteArrayOutputStream getActa(String meetingId) throws Exception{
		Meeting meeting;
		try{
			meeting =this.getMeeting(meetingId);
		}catch (Exception e){
			throw new MeetingNotFound();
		}
		
		Document document = new Document();
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;
		HeaderFooter event = new HeaderFooter(meeting.getDate());
		docWriter = PdfWriter.getInstance(document, baosPDF);
		docWriter.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		docWriter.setPageEvent(event);
		document.open();
		
		//metadata
		document.addTitle(meeting.getTitle()+" Acta");

		document.add(new Paragraph(" "));
		
		//Title
		 Paragraph title = new Paragraph("Acta of "+meeting.getTitle(), titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	   
	    addEmptyLine(title, 1);
	    
	    document.add(title);
	    
	    //Description
	    Paragraph descriptionPar=new Paragraph();
	    descriptionPar.add(new Paragraph("Description" ,titlePointFont ));
	    descriptionPar.add(new Paragraph(meeting.getDescription(),textFont ));
	    addEmptyLine(descriptionPar, 1);
	    document.add(descriptionPar);
	    
	    
	    //Points
	    int index =1;
	    for(PointOfAgenda point:meeting.getAgenda()){
	    	Paragraph pointPar = new Paragraph();
	    	pointPar.add(new Paragraph(index+". "+point.getName(), titlePointFont));
	    	pointPar.add(new Paragraph(point.getComment(), textFont));
	    	addEmptyLine(pointPar, 2);
	    	document.add(pointPar);
	    	index+=1;
	    }
		
	    document.close();	    
		
		return baosPDF;
			
		
	}
	
	private static long calculateMeetingTime(ArrayList<PointOfAgenda> agenda){
		long totalMili = 0l;
		for(PointOfAgenda point: agenda){
			totalMili+=point.getDuration()*1000; //seconds to miliseconds
		}
		
		return totalMili;
	}
	
	 private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  }

}
