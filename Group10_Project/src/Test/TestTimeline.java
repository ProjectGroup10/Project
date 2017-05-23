package Test;

import application.*;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTimeline {
    private final LocalDate today = LocalDate.now();

	@Before
	public void setUp() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		Timeline t = new DayTimeline("title", today, today.plusDays(2));
		assertEquals(t.getTitle(),"title");
		assertEquals(t.getStartDate(),today);
		assertEquals(t.getEndDate(),today.plusDays(2));
	}
	
	@Test
	public void testSpecialTitle()
	{
		Timeline t = new DayTimeline("t_itle£$@", today, today.plusDays(2));
		assertEquals(t.getTitle(),"t_itle£$@");
		assertEquals(t.getStartDate(),today);
		assertEquals(t.getEndDate(),today.plusDays(2));
		
		Timeline t1 = new DayTimeline("t tle£$@t tle£$@t tle£$@t tle£$@t tle£$@", today, today.plusDays(2));
		assertEquals(t1.getTitle(),"t tle£$@t tle£$@t tle£$@t tle£$@t tle£$@");
		assertEquals(t1.getStartDate(),today);
		assertEquals(t1.getEndDate(),today.plusDays(2));
		
		Timeline t2 = new DayTimeline("t t34r342le£$@", today, today.plusDays(2));
		assertEquals(t2.getTitle(),"t t34r342le£$@");
		assertEquals(t2.getStartDate(),today);
		assertEquals(t2.getEndDate(),today.plusDays(2));
	    }
	
	@Test
	public void testDifferentDate()
	{
		LocalDate date = LocalDate.parse("2017-04-17");
		LocalDate date1 = LocalDate.parse("1677-04-17");
		LocalDate date2 = LocalDate.parse("2025-04-17");

		Timeline t = new DayTimeline("title", date, date.plusDays(10));
		assertEquals(t.getStartDate(),date);
		assertEquals(t.getEndDate(),date.plusDays(10));
		
		Timeline t_ = new DayTimeline("title", date1, date1.plusDays(10));
		assertEquals(t_.getStartDate(),date1);
		assertEquals(t_.getEndDate(),date1.plusDays(10));
		
		Timeline t__ = new DayTimeline("title", date2, date2.plusDays(10));
		assertEquals(t__.getStartDate(),date2);
		assertEquals(t__.getEndDate(),date2.plusDays(10));
		
		Timeline t1 = new DayTimeline("title", date, date.plusDays(100));
		assertEquals(t1.getStartDate(),date);
		assertEquals(t1.getEndDate(),date.plusDays(100));
		
		Timeline t1_ = new DayTimeline("title", date1, date1.plusDays(100));
		assertEquals(t1_.getStartDate(),date1);
		assertEquals(t1_.getEndDate(),date1.plusDays(100));
		
		Timeline t1__ = new DayTimeline("title", date2, date2.plusDays(100));
		assertEquals(t1__.getStartDate(),date2);
		assertEquals(t1__.getEndDate(),date2.plusDays(100));

		Timeline t2 = new DayTimeline("title", date, date.plusDays(1000));
		assertEquals(t2.getStartDate(),date);
		assertEquals(t2.getEndDate(),date.plusDays(1000));
		
		Timeline t2_ = new DayTimeline("title", date1, date1.plusDays(1000));
		assertEquals(t2_.getStartDate(),date1);
		assertEquals(t2_.getEndDate(),date1.plusDays(1000));
		
		Timeline t2__ = new DayTimeline("title", date2, date2.plusDays(1000));
		assertEquals(t2__.getStartDate(),date2);
		assertEquals(t2__.getEndDate(),date2.plusDays(1000));

	}
	
	@Test 
	public void testListOfTimeline()
	{
		LocalDate date = LocalDate.parse("2017-04-17");

		for(int i = 0; i < 10000; i++)
		{
			Timeline t = new DayTimeline("title" + i, date.plusDays(i), date.plusDays(100 + i));
			assertEquals(t.getTitle(), "title" + i);
			assertEquals(t.getStartDate(), date.plusDays(i));
			assertEquals(t.getEndDate(), date.plusDays(100 + i));
		}
	}
}
