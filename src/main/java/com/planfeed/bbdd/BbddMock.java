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

import java.util.ArrayList;
import java.util.HashMap;

import com.planfeed.bbdd.interfaces.Querys;
import com.planfeed.elements.Meeting;
import com.planfeed.elements.PointOfAgenda;
import com.planfeed.elements.Token;

public class BbddMock {
	
	
	private static BbddMock uniqueInstance;
	public HashMap<String,Meeting> mock = new HashMap<String,Meeting>();
	
	
	private BbddMock(){}

	public static BbddMock getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new BbddMock();
			uniqueInstance.firstMeeting();

		}
		return uniqueInstance;
	}
	private void firstMeeting(){
		//create a meeting
				Meeting m1 = new Meeting();
				m1.setMeetingId("123456");
				m1.setTitle("Mock Meeting");
				long milis = 1322018752992l;
				m1.setDate(milis);
				m1.setDescription("Mock description");
				PointOfAgenda p1 = new PointOfAgenda("dfg","point1", 200,100, "comment point 1");
				PointOfAgenda p2 = new PointOfAgenda("srffs","point2", 200,100, "comment point 2");
				PointOfAgenda p3 = new PointOfAgenda("thggh","point3", 200,100, "comment point 3");
				ArrayList<PointOfAgenda> agenda = new ArrayList<PointOfAgenda>();
				agenda.add(p1);
				agenda.add(p2);
				agenda.add(p3);
				m1.setAgenda(agenda);
				mock.put("123456", m1);
	}

	public Meeting getMeeting(String id) throws Exception {
		if(mock.get(id)!=null){
			
			return mock.get(id);

		}else {
			throw new Exception();
		}
	}



	public void putMeeting(Meeting meeting) throws Exception {
		try{
			mock.put(meeting.getMeetingId(), meeting);
		}catch ( Exception e){
			throw new Exception();
		}

	}




	public void deleteMeeting(String meetingId) throws Exception {
		try{
			mock.remove(meetingId);
		}catch ( Exception e){
			throw new Exception();
		}
		
	}

	


}
