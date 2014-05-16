import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.planfeed.elements.Meeting;
import com.planfeed.services.MeetingServiceImpl;


public class MeetingServiceImplTest {

	static MeetingServiceImpl meetingService;
	
	@BeforeClass
	public static void init(){
		meetingService= new MeetingServiceImpl();
	}
	
	@Test
	public void putMeetingTest(){
		
		Meeting meeting = new Meeting();
		meeting.setCalendarEventId("al2eb3rn08aa49vgvbvcjma44c");
		
		Meeting result;
		try{
			result=meetingService.putMeeting(meeting, false);
			assertTrue(result.getCalendarId().equalsIgnoreCase("santi.munozm@gmail.com"));
		}catch(Exception e){
			fail();
		}
		
	}

}
