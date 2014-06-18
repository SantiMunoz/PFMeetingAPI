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
package com.planfeed.bbdd.interfaces;

import java.util.ArrayList;

import javax.ws.rs.core.StreamingOutput;

import com.planfeed.elements.Meeting;
import com.planfeed.elements.NotificationsChannel;
import com.planfeed.elements.Token;

public interface Querys {
	
	/**
	 * Busca en la base de datos un meeting con el id especificado.
	 * @param id ID del meeting que se quiere.
	 * @return Meeting con el id especificado.
	 * @throws Exception
	 */
	public Meeting getMeeting(String id) throws Exception;
	
	/**
	 * Guarda o actualiza un meeting a la base de datos.
	 * @param meeting
	 * @throws Exception
	 */
	public void putMeeting(Meeting meeting) throws Exception;
	
	/**
	 * Guarda un token en la base de datos.
	 * @param token
	 * @throws Exception
	 */
	public void putToken(Token token)throws Exception;
	
	/**
	 * Busca un token a partir del email a la base de datos.
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Token getTokenByEmail(String email)throws Exception;
	
	/**
	 * Busca todos los meetings que pertenecen al calendario especificado.
	 * @param calendarId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Meeting> getMeetingsbyCalendarId(String calendarId) throws Exception;
	
	/**
	 * Guarda un meeting sin la agenda.
	 * @param meeting
	 * @throws Exception
	 */
	public void putMeetingOnly(Meeting meeting) throws Exception;
	
	/**
	 * Busca un meeting a partir del id correspondiente al evento de Google Calendar
	 * @param eventId
	 * @return
	 * @throws Exception
	 */
	public String getMeetingIdByEventId(String eventId) throws Exception;
	
	/**
	 * Buscar un NotificationChannel en la base de datos.
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public NotificationsChannel getChannel(String channelId) throws Exception;
	
	/**
	 * Guardar un NotificationChannel en la base de datos.
	 * @param notiChannel
	 * @throws Exception
	 */
	public void putChannel(NotificationsChannel notiChannel) throws Exception;

}
