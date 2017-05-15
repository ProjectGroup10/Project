package Test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Event;

public class TestDurationEvent {
	private BufferedImage image = null;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/*
	 * Test title 
	 */
	@Test
	public void testTitle() {
		Event e = new Event("Title", "", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e.getTitleEvent(),"Title");
	
		Event e1 = new Event("T*&_@£itle", "", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e1.getTitleEvent(),"T*&_@£itle");
		
		Event e2 = new Event("T £3453itle", "", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e2.getTitleEvent(),"T £3453itle");
		
		Event e3 = new Event("T*&_@£itleT*&_@£itleT*&_@£itleT*&_@£itle", "", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e3.getTitleEvent(),"T*&_@£itleT*&_@£itleT*&_@£itleT*&_@£itle");
	}
	
	/*
	 * Test Start Date and EndDate 
	 */
	@Test
	public void testDifferentDate()
	{
		LocalDate date = LocalDate.parse("2017-04-17");
		LocalDate date1 = LocalDate.parse("1677-04-17");
		LocalDate date2 = LocalDate.parse("2025-04-17");

		Event e = new Event("Title", "", date, date.plusDays(10), false, image,"png" );
		assertEquals(e.getStartDatePickerEvent(),date);
		assertEquals(e.getEndDatePickerEvent(),date.plusDays(10));
		
		Event e_ = new Event("Title", "", date1, date1.plusDays(10), false, image,"png" );
		assertEquals(e_.getStartDatePickerEvent(),date1);
		assertEquals(e_.getEndDatePickerEvent(),date1.plusDays(10));
		
		Event e__ = new Event("Title", "", date2, date2.plusDays(10), false, image,"png" );
		assertEquals(e__.getStartDatePickerEvent(),date2);
		assertEquals(e__.getEndDatePickerEvent(),date2.plusDays(10));		
		
		Event e1 = new Event("Title", "", date, date.plusDays(100), false, image,"png" );
		assertEquals(e1.getStartDatePickerEvent(),date);
		assertEquals(e1.getEndDatePickerEvent(),date.plusDays(100));
		
		Event e1_ = new Event("Title", "", date1, date1.plusDays(100), false, image,"png" );
		assertEquals(e1_.getStartDatePickerEvent(),date1);
		assertEquals(e1_.getEndDatePickerEvent(),date1.plusDays(100));
		
		Event e1__ = new Event("Title", "", date2, date2.plusDays(100), false, image,"png" );
		assertEquals(e1__.getStartDatePickerEvent(),date2);
		assertEquals(e1__.getEndDatePickerEvent(),date2.plusDays(100));
		
		Event e2 = new Event("Title", "", date, date.plusDays(1000), false, image,"png" );
		assertEquals(e2.getStartDatePickerEvent(),date);
		assertEquals(e2.getEndDatePickerEvent(),date.plusDays(1000));
		
		Event e2_ = new Event("Title", "", date1, date1.plusDays(1000), false, image,"png" );
		assertEquals(e2_.getStartDatePickerEvent(),date1);
		assertEquals(e2_.getEndDatePickerEvent(),date1.plusDays(1000));
		
		Event e2__ = new Event("Title", "", date2, date2.plusDays(1000), false, image,"png" );
		assertEquals(e2__.getStartDatePickerEvent(),date2);
		assertEquals(e2__.getEndDatePickerEvent(),date2.plusDays(1000));		
	}

	/*
	 * Test description 
	 */
	@Test
	public void testDifferentDis()
	{
		Event e = new Event("", "description", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e.getDescEvent(),"description");
	
		Event e1 = new Event("", "de^&_~!scri_.,ption", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e1.getDescEvent(),"de^&_~!scri_.,ption");
		
		Event e2 = new Event("", "de 54^8scription", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e2.getDescEvent(),"de 54^8scription");
		
		Event e3 = new Event("","de 54^8scriptiondi 54^8scriptiondi^&_~!scri_.,ptiondi^&_~!scri_.,ption", LocalDate.now(), LocalDate.now().plusDays(10), false, image,"png" );
		assertEquals(e3.getDescEvent(),"de 54^8scriptiondi 54^8scriptiondi^&_~!scri_.,ptiondi^&_~!scri_.,ption");
	}
	
	/*
	 * Test List of Event
	 */
	@Test 
	public void testlistOfEvent()
	{
		LocalDate date = LocalDate.parse("2017-04-17");

		for(int i = 0; i < 10000; i++)
		{
			Event e = new Event("title" + i, "description" + i, date.plusDays(i), date.plusDays(100 + i), false, image,"png" );
			assertEquals(e.getTitleEvent(),"title" + i);
			assertEquals(e.getDescEvent(),"description" + i);
			assertEquals(e.getStartDatePickerEvent(),date.plusDays(i));
			assertEquals(e.getEndDatePickerEvent(),date.plusDays(100 + i));
		}
	}
}
