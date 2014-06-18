package com.planfeed.services.interfaces;

public interface GoogleService {
	
	/**
	 * A partir de un Acces Code genera un token y un refresh token y los guarda en la base de datos.
	 * @param email Email del propietario del Acces Code
	 * @param code Acces Code de OAuth2
	 * @throws Exception
	 */
	public void storeToken (String email, String code)throws Exception;
	
	/**
	 * Procesa las notificaciones que envia la API de Google Calendar. Identifica el calendario que 
	 * se ha modificado y compureba cual de las reuniones de la base de datos que pertenecen a ese
	 * calendario se ha actualizado, para luego actualizar esta reunion a la base de datos.
	 * @param resourceUri URL con el calendario modificado
	 * @throws Exception
	 */
	public void newNotification(String resourceUri) throws Exception;
	
	/**
	 * Retorna el calendarId a partir de la resourceUri
	 * @param resourceUri
	 * @return El calendarId
	 */
	public String getCalendarId(String resourceUri);
	
	/**
	 * Comprueba la fecha de caducidad del canal y genera una nuevo si esta a apunto de caducar.
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public String checkChannelId(String channelId) throws Exception;
	
	/**
	 * Genera un nuevo channelId.
	 * @param channelId
	 * @return
	 */
	public String newChannelId(String channelId);

}
