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
package com.planfeed.services.interfaces;

import java.io.ByteArrayOutputStream;
import com.planfeed.elements.Meeting;

public interface MeetingService {

	public Meeting getMeeting(String id) throws Exception;
	public void modifyStatus(String meetingId, String newStatus) throws Exception;
	public void deleteMeeting(String meetingId) throws Exception;
	public Meeting putMeeting(Meeting meeting, boolean updateEventCalendar) throws Exception;
	public ByteArrayOutputStream getActa(String meetingId) throws Exception;
}
