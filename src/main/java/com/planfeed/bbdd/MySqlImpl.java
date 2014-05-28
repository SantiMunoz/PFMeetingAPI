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
package com.planfeed.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.planfeed.bbdd.interfaces.Querys;
import com.planfeed.elements.Meeting;
import com.planfeed.elements.PointOfAgenda;
import com.planfeed.elements.Token;
import com.planfeed.services.exceptions.MeetingNotFound;


/**
 * Per utilitzar aquesta classe es necessari tindre creades les següents taules:
 * CREATE TABLE Meeting ( meetingId VARCHAR(20) NOT NULL, title VARCHAR(200), date BIGINT, description VARCHAR(10000), init BIGINT, pauseDate BIGINT, status VARCHAR(10), initOffTime BIGINT, creatorEmail VARCHAR(100),calendarEventId VARCHAR(200), calendarId VARCHAR(200), timeFinish BIGINT, PRIMARY KEY (meetingId));
 * CREATE TABLE PointOfAgenda ( pointId VARCHAR(20) NOT NULL, name VARCHAR(200), duration MEDIUMINT, originalDuration MEDIUMINT, comment VARCHAR(10000),position TINYINT, meetingId VARCHAR(20) NOT NULL, PRIMARY KEY (pointId, meetingId));
 * CREATE TABLE Tokens ( email VARCHAR(50) NOT NULL, token VARCHAR(200), refreshToken VARCHAR(200) NOT NULL, PRIMARY KEY (email));

 * @author santi
 *
 */
public class MySqlImpl implements Querys {
	
	public MySqlImpl(){}


	
	public Meeting getMeeting(String id) throws Exception {
		Meeting meeting = new Meeting();
		ArrayList<PointOfAgenda> agenda = new ArrayList<PointOfAgenda>();
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		ResultSet rs=null;
//		String meetingQuery = "SELECT * FROM Meeting WHERE meetingId='"+id+"';";
//		String agendaQuery = "SELECT * FROM PointOfAgenda WHERE meetingId='"+id+"' ORDER BY position;";
		
		try{
			st = conn.prepareStatement("SELECT * FROM Meeting WHERE meetingId=?;");
			st.setString(1, id);
			rs= st.executeQuery();
			if(rs.next()){
				do{
					meeting.setMeetingId(rs.getString("meetingId"));
					meeting.setTitle(rs.getString("title"));
					meeting.setDate(rs.getLong("date"));
					meeting.setDescription(rs.getString("description"));
					meeting.setInit(rs.getLong("init"));
					meeting.setPauseDate(rs.getLong("pauseDate"));
					meeting.setStatus(rs.getString("status"));
					meeting.setInitOffTime(rs.getLong("initOffTime"));
					meeting.setCreatorEmail(rs.getString("creatorEmail"));
					meeting.setCalendarEventId(rs.getString("calendarEventId"));
					meeting.setCalendarId(rs.getString("calendarId"));
					meeting.setTimeFinish(rs.getLong("timeFinish"));
				}while(rs.next());
				
			}else{
				throw new MeetingNotFound();
			}
			st = conn.prepareStatement("SELECT * FROM PointOfAgenda WHERE meetingId=? ORDER BY position;");
			st.setString(1, id);
			rs = st.executeQuery();
			
			while(rs.next()){
				PointOfAgenda point = new PointOfAgenda();
				
				point.setPointId(rs.getString("pointId"));
				point.setComment(rs.getString("comment"));
				point.setDuration(rs.getLong("duration"));
				point.setOriginalDuration(rs.getLong("originalDuration"));
				point.setName(rs.getString("name"));
				
				agenda.add(point);
			}
			meeting.setAgenda(agenda);
			
			
			conn.close();
			rs.close();
			st.close();
			return meeting;
		} catch(MeetingNotFound ex){
			conn.close();
			rs.close();
			st.close();
			throw new MeetingNotFound();
		}catch(Exception e){
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
			
		}
		
	}

	public ArrayList<Meeting> getMeetingsbyCalendarId(String calendarId) throws Exception{
		ArrayList<Meeting> meetingsList = new ArrayList<Meeting>();
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		ResultSet rs=null;
		//String meetingQuery = "SELECT * FROM Meeting WHERE calendarId='"+calendarId+"';";
		
		
		try{
			st = conn.prepareStatement("SELECT * FROM Meeting WHERE calendarId=?;");
			st.setString(1, calendarId);
			rs= st.executeQuery();
			if(rs.next()){
				Meeting meeting = new Meeting();
				do{
					meeting = new Meeting();
					meeting.setMeetingId(rs.getString("meetingId"));
					meeting.setTitle(rs.getString("title"));
					meeting.setDate(rs.getLong("date"));
					meeting.setDescription(rs.getString("description"));
					meeting.setInit(rs.getLong("init"));
					meeting.setPauseDate(rs.getLong("pauseDate"));
					meeting.setStatus(rs.getString("status"));
					meeting.setInitOffTime(rs.getLong("initOffTime"));
					meeting.setCreatorEmail(rs.getString("creatorEmail"));
					meeting.setCalendarEventId(rs.getString("calendarEventId"));
					meeting.setCalendarId(rs.getString("calendarId"));
					meeting.setTimeFinish(rs.getLong("timeFinish"));
					meetingsList.add(meeting);
				}while(rs.next());
				
			}else{
				throw new MeetingNotFound();
			}
			
			
			conn.close();
			rs.close();
			st.close();
			return meetingsList;
		} catch(MeetingNotFound ex){
			conn.close();
			rs.close();
			st.close();
			throw new MeetingNotFound();
		}catch(Exception e){
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
			
		}
	}

	public void putMeetingOnly(Meeting meeting) throws Exception{
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;

		try{

			st =conn.prepareStatement( "INSERT INTO Meeting (meetingId, title, date, description, init, pauseDate, status, initOffTime, creatorEmail, calendarEventId,calendarId, timeFinish) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?) " +
				"ON DUPLICATE KEY UPDATE title=?, date=?, description=?, init=?, pauseDate=?, status=?, initOffTime=?, creatorEmail=?, calendarEventId=?, calendarId=?, timeFinish=?;");
			st.setString(1, meeting.getMeetingId());
			st.setString(2,checkNull(meeting.getTitle()) );
			st.setLong(3, meeting.getDate());
			st.setString(4,checkNull(meeting.getDescription()));
			st.setLong(5, meeting.getInit());
			st.setLong(6, meeting.getPauseDate());
			st.setString(7,checkNull(meeting.getStatus()));
			st.setLong(8, meeting.getInitOffTime());
			st.setString(9,checkNull(meeting.getCreatorEmail()));
			st.setString(10,checkNull(meeting.getCalendarEventId()));
			st.setString(11,checkNull(meeting.getCalendarId()));
			st.setLong(12, meeting.getTimeFinish());
			st.setString(13,checkNull(meeting.getTitle()) );
			st.setLong(14, meeting.getDate());
			st.setString(15,checkNull(meeting.getDescription()));
			st.setLong(16, meeting.getInit());
			st.setLong(17, meeting.getPauseDate());
			st.setString(18,checkNull(meeting.getStatus()));
			st.setLong(19, meeting.getInitOffTime());
			st.setString(20,checkNull(meeting.getCreatorEmail()));
			st.setString(21,checkNull(meeting.getCalendarEventId()));
			st.setString(22,checkNull(meeting.getCalendarId()));
			st.setLong(23, meeting.getTimeFinish());
			st.executeUpdate();
			
			
		}catch(SQLException e){
			e.printStackTrace();
			conn.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}catch(Exception e){
			e.printStackTrace();
			conn.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}
	}


	public void putMeeting(Meeting meeting) throws Exception {
		
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		ResultSet rs=null;
		int position=1;

		try{
			
			
			//put meeting
			putMeetingOnly(meeting);
			
			//put agenda
			for(PointOfAgenda point: meeting.getAgenda()){
				
//				String agendaQuery = "INSERT INTO PointOfAgenda (pointId, name, duration, originalDuration, comment, position, meetingId) "+
//						"VALUES ('"+checkNull(point.getPointId())+"', '"+checkNull(point.getName())+"', "+point.getDuration()+", "+point.getOriginalDuration()+", '"+checkNull(point.getComment())+"', "+position+", '"+checkNull(meeting.getMeetingId())+"') "+
//						"ON DUPLICATE KEY UPDATE name='"+checkNull(point.getName())+"', duration="+point.getDuration()+", originalDuration="+point.getOriginalDuration()+", comment='"+checkNull(point.getComment())+"', position="+position+", meetingId='"+meeting.getMeetingId()+"';";
				
				st = conn.prepareStatement("INSERT INTO PointOfAgenda (pointId, name, duration, originalDuration, comment, position, meetingId) "+
						"VALUES (?,?,?,?,?,?,?) "+
						"ON DUPLICATE KEY UPDATE name=?, duration=?, originalDuration=?, comment=?, position=?, meetingId=?;");
				st.setString(1, checkNull(point.getPointId()));
				st.setString(2, checkNull(point.getName()));
				st.setLong(3, point.getDuration());
				st.setLong(4, point.getOriginalDuration());
				st.setString(5, checkNull(point.getComment()));
				st.setInt(6, position);
				st.setString(7, checkNull(meeting.getMeetingId()));
				st.setString(8, checkNull(point.getName()));
				st.setLong(9, point.getDuration());
				st.setLong(10, point.getOriginalDuration());
				st.setString(11, checkNull(point.getComment()));
				st.setInt(12, position);
				st.setString(13, checkNull(meeting.getMeetingId()));
				
				
				st.executeUpdate();
				position+=1;
			}
			
			String queryToDelete="DELETE FROM PointOfAgenda WHERE pointId IN (";
			//String getPointsQuery="SELECT pointId FROM PointOfAgenda WHERE meetingId='"+meeting.getMeetingId()+"'";
			st = conn.prepareStatement("SELECT pointId FROM PointOfAgenda WHERE meetingId=?");
			st.setString(1, meeting.getMeetingId());
			rs= st.executeQuery();
			boolean first=true;
			ArrayList<String> pointsToDelete = new ArrayList<String>();
			while(rs.next()){ 
				boolean trobat=false;
				Iterator it=meeting.getAgenda().iterator();
				while(it.hasNext()&& !trobat){
					PointOfAgenda point = (PointOfAgenda) it.next();
					if(point.getPointId().equalsIgnoreCase(rs.getString("pointId"))) trobat=true;
				}
				if(!trobat){
					if(first){
						queryToDelete+="?";
						pointsToDelete.add(rs.getString("pointId"));
						first=false;
					}else{
						queryToDelete+=",?";
						pointsToDelete.add(rs.getString("pointId"));
					}
				}	
			}
			queryToDelete+=");";
			if(!first){
				st = conn.prepareStatement(queryToDelete);
				int index = 1;
				for(String id:pointsToDelete){
					st.setString(index, id);
					index++;
				}
				st.executeUpdate();
			}

			conn.close();
			rs.close();
			st.close();
			
		}catch(SQLException e){
			e.printStackTrace();
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}catch(Exception e){
			e.printStackTrace();
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}

	}
	
	public void putToken(Token token)throws Exception{
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		
		String tokenQuery = "INSERT INTO Tokens ( email, token, refreshToken) "
				+ "VALUES (?,?,?)"
						+ "ON DUPLICATE KEY UPDATE  token=?, refreshToken=?;";
	
		try{
			st = conn.prepareStatement(tokenQuery);
			st.setString(1, token.getEmail());
			st.setString(2, token.getToken());
			st.setString(3, token.getResfreshToken());
			st.setString(4, token.getToken());
			st.setString(5, token.getResfreshToken());
			st.executeUpdate();
			
			conn.close();
			st.close();
			
		}catch(SQLException e){
			e.printStackTrace();
			conn.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}catch(Exception e){
			e.printStackTrace();
			conn.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}
	}
	
	
	public Token getTokenByEmail(String email)throws Exception{
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		Token token = new Token();
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		ResultSet rs=null;
		String query = "SELECT * FROM Tokens WHERE email=?;";
		try{
			st = conn.prepareStatement(query);
			st.setString(1, email);
			rs= st.executeQuery();
			if(rs.next()){
				token.setEmail(email);
				token.setToken(rs.getString("token"));
				token.setResfreshToken(rs.getString("refreshToken"));
				
				
			}else{
				
				throw new Exception();
			}
			
			conn.close();
			rs.close();
			st.close();
			return token;
		}catch(SQLException e){
			e.printStackTrace();
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
		}catch(Exception e){
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
			
		}
	}
	
	
	private String checkNull(String data){
		if(data==null){
			return "";
			
		}else{
			return data;
		}
	}
	

	
	public String getMeetingIdByEventId(String eventId) throws Exception{
		MySqlConnection mysqlconn = null;
		Connection conn = null;
		try{
			mysqlconn = new MySqlConnection();
			conn = mysqlconn.getConn();
		}catch(Exception e){
			throw new Exception(e.getStackTrace().toString());
		}
		PreparedStatement st=null;
		ResultSet rs=null;
		String meetingIdRes=null;
		String query = "SELECT meetingId FROM Meeting WHERE calendarEventId=?;";
		try{
			st = conn.prepareStatement(query);
			st.setString(1, eventId);
			rs= st.executeQuery();
			if(rs.next()){
				meetingIdRes = rs.getString("meetingId");
				
				
			}else{
				
				throw new MeetingNotFound();
			}
			
			conn.close();
			rs.close();
			st.close();
			return meetingIdRes;
		}catch(MeetingNotFound ex){
			conn.close();
			rs.close();
			st.close();
			throw new MeetingNotFound();
		}catch(Exception e){
			conn.close();
			rs.close();
			st.close();
			throw new Exception(e.getStackTrace().toString());
			
		}

	}


}
