import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Channel;
import com.planfeed.others.GeneralMethods;
import com.planfeed.services.GoogleServiceImpl;


public class GoogleServiceImplTest {
	static GoogleServiceImpl gsimp;
	static GeneralMethods generalMeth;
	@BeforeClass
	public static void beforClass(){
		gsimp=  new GoogleServiceImpl();
		generalMeth =new GeneralMethods();
	}
	
	@Test
	public void getCalendarIdTest() {
		
		String result = gsimp.getCalendarId("https://content.googleapis.com/calendar/v3/calendars/my_calendar@gmail.com/events?alt=json&alt=json");
		
		assertTrue(result.equalsIgnoreCase("my_calendar@gmail.com"));
	}
	
	@Test
	public void filterDescriptionTest(){
		String description = "novvaaa descripciokk hhhss \naa \nMeeting in Plan&Feedback Meeting Tool:\nhttp://pfmeeting.com/#/meeting/6dXiDvWtM5Tt";
		String meetingId="6dXiDvWtM5Tt";
		String result = generalMeth.filterDescription(description, meetingId);

		assertTrue(result.equalsIgnoreCase("novvaaa descripciokk hhhss \naa "));
		
		description = " \n \n  \nMeeting in Plan&Feedback Meeting Tool:\nhttp://pfmeeting.com/#/meeting/6dXiDvWtM5Tt\n  \n";
		meetingId="6dXiDvWtM5Tt";
		result = generalMeth.filterDescription(description, meetingId);

		assertTrue(result.equalsIgnoreCase(""));
	}
	
	@Test
	public void newNotificationTest() {
		try{
			gsimp.newNotification("https://content.googleapis.com/calendar/v3/calendars/santi.munozm@gmail.com/events");
		}catch(Exception e){
			fail();
			
		}
		
	}
	
	@Test
	public void createNewChannelId(){
		String channelId = "f4brln8m8v192hogroupcalendargooglecom3216";
		String newChannelId=gsimp.newChannelId(channelId);
		assertEquals("f4brln8m8v192hogroupcalendargooglecom3217", newChannelId);
	}
	
	@Test
	public void reciveNotification(){
//		Querys bbdd = new MySqlImpl();
//		Token tok = new Token();
//		tok.setEmail("santi.munozm@gmail.com");
//		tok.setToken("ya29.KgDUWKMjHj9H_R8AAABELrTV8wHZi-MI0LEDn7fT3VisoLNW7mfjoMsy7-DQMw");
//		tok.setResfreshToken("1/eTZ4qOHSFxQjPk1l7tq8VKCWgVtvWn6eqcnjXs4NFsw");
//		Meeting mmet= new Meeting();
//		mmet.setMeetingId("ur58M7Fb5xmb");
//		mmet.setTitle("proba3");
//		mmet.setDate(1401967800000l);
//		mmet.setCreatorEmail("santi.munozm@gmail.com");
//		mmet.setCalendarEventId("iotfbg63ibrrlu628ieg00clug");
//		mmet.setCalendarId("f4brln8m8v192holog73ls92p8@group.calendar.google.com");
//		try {
//			bbdd.putToken(tok);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		String resourceUri = "https://content.googleapis.com/calendar/v3/calendars/f4brln8m8v192holog73ls92p8@group.calendar.google.com/events?alt=json&alt=json";
		try{
			gsimp.newNotification(resourceUri);
		}catch (Exception e){
			//e.printStackTrace();
		}
	}
	
	@Test
	public void checkChannelIdTest(){
		try {
			String newChannel = gsimp.checkChannelId("akdjhlagdlagywdaigydailywidaygooglecom1111");
			System.out.println(newChannel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void createChannel(){
//		try {
//			Calendar client = generalMeth.getClient("santi.munozm@gmail.com");
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("ttl", "3600");
//			Channel request =new Channel()
//				.setId("probacanalcreatmanualment354685468486")
//				.setType("web_hook")
//				.setAddress("https://pfmeetingapi.appspot.com/api/googlerest/notifications")
//				.setResourceId("f4brln8m8v192holog73ls92p8@group.calendar.google.com")
//				.setExpiration(3600l)
//				.setToken("probacanalcreatmanualment354685468486");
//			System.out.println("Entra");
//			client.events().watch("f4brln8m8v192holog73ls92p8@group.calendar.google.com", request).execute();
//			System.out.println("Entra2");
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("Entra3");
//			e.printStackTrace();
//			System.out.println("Entra4");
//		}
//	}

}
