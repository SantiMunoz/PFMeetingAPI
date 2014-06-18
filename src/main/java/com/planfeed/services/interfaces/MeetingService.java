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

	/**
	 * Busca en la base de datos un meeting con el id especificado.
	 * Si no lo encuentra retorna error.
	 * @param id ID del meeting que queremos.
	 * @return Meeting con el ID especificado.
	 * @throws Exception
	 */
	public Meeting getMeeting(String id) throws Exception;
	
	/**
	 * Modifica el atributo status del meeting correspondiente al meetingId especificado.
	 * @param meetingId ID del meeting al que queremos cambiar el status.
	 * @param newStatus Nuevo status a aplicar.
	 * @throws Exception
	 */
	public void modifyStatus(String meetingId, String newStatus) throws Exception;
	
	/**
	 * Funciona con doble funcionalidad:
	 * 1- Si pasamos por parámetro un meeting sin id se le asigna una y lo guarda en la base de datos.
	 * 2- Si pasamos por parámetro un meeting con id actualiza al meeting ya existente en la base de datos.
	 * @param meeting Meeting que queremos guardar o modificar.
	 * @param updateEventCalendar Flag que nos indica si es un evento sincronizado con google calendar.
	 * @return Meeting actualizado.
	 * @throws Exception
	 */
	public Meeting putMeeting(Meeting meeting, boolean updateEventCalendar) throws Exception;
	
	/**
	 * Genera un pdf con el acta de la reunión.
	 * @param meetingId
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream getActa(String meetingId) throws Exception;
}
