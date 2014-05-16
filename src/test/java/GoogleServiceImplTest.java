import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

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
	
	

}
