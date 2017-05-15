package Test;

import application.*;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEvent {
	private BufferedImage image = null;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTitle() {
		Event e = new Event("Title", "", LocalDate.now(), false, image,"png" );
		assertEquals(e.getTitleEvent(),"Title");
	
		Event e1 = new Event("T*&_@£itle", "", LocalDate.now(), false, image,"png" );
		assertEquals(e1.getTitleEvent(),"T*&_@£itle");
		
		Event e2 = new Event("T £3453itle", "", LocalDate.now(), false, image,"png" );
		assertEquals(e2.getTitleEvent(),"T £3453itle");
		
		Event e3 = new Event("T*&_@£itleT*&_@£itleT*&_@£itleT*&_@£itle", "", LocalDate.now(), false, image,"png" );
		assertEquals(e3.getTitleEvent(),"T*&_@£itleT*&_@£itleT*&_@£itleT*&_@£itle");
	}
	
	@Test
	public void testDifferentDate()
	{
		LocalDate date = LocalDate.parse("2017-04-17");
		LocalDate date1 = LocalDate.parse("1677-04-17");
		LocalDate date2 = LocalDate.parse("2025-04-17");

		Event e = new Event("Title", "", date, false, image,"png" );
		assertEquals(e.getStartDatePickerEvent(),date);
		
		Event e_ = new Event("Title", "", date1, false, image,"png" );
		assertEquals(e_.getStartDatePickerEvent(),date1);
		
		Event e__ = new Event("Title", "", date2, false, image,"png" );
		assertEquals(e__.getStartDatePickerEvent(),date2);
		
		Event e1 = new Event("Title", "", date, false, image,"png" );
		assertEquals(e1.getStartDatePickerEvent(),date);
		
		Event e1_ = new Event("Title", "", date1, false, image,"png" );
		assertEquals(e1_.getStartDatePickerEvent(),date1);
		
		Event e1__ = new Event("Title", "", date2, false, image,"png" );
		assertEquals(e1__.getStartDatePickerEvent(),date2);
		
		Event e2 = new Event("Title", "", date, false, image,"png" );
		assertEquals(e2.getStartDatePickerEvent(),date);
		
		Event e2_ = new Event("Title", "", date1, false, image,"png" );
		assertEquals(e2_.getStartDatePickerEvent(),date1);
		
		Event e2__ = new Event("Title", "", date2, false, image,"png" );
		assertEquals(e2__.getStartDatePickerEvent(),date2);
	}

	@Test
	public void testDifferentDis()
	{
		Event e = new Event("", "discription", LocalDate.now(), false, image,"png" );
		assertEquals(e.getDescEvent(),"discription");
	
		Event e1 = new Event("", "di^&_~!scri_.,ption", LocalDate.now(), false, image,"png" );
		assertEquals(e1.getDescEvent(),"di^&_~!scri_.,ption");
		
		Event e2 = new Event("", "di 54^8scription", LocalDate.now(), false, image,"png" );
		assertEquals(e2.getDescEvent(),"di 54^8scription");
		
		Event e3 = new Event("","di 54^8scriptiondi 54^8scriptiondi^&_~!scri_.,ptiondi^&_~!scri_.,ption", LocalDate.now(), false, image,"png" );
		assertEquals(e3.getDescEvent(),"di 54^8scriptiondi 54^8scriptiondi^&_~!scri_.,ptiondi^&_~!scri_.,ption");
	}
	
	@Test 
	public void testlistOfEvent()
	{
		LocalDate date = LocalDate.parse("2017-04-17");

		for(int i = 0; i < 10000; i++)
		{
			Event e = new Event("title" + i, "discription" + i, date.plusDays(i), false, image,"png" );
			assertEquals(e.getTitleEvent(),"title" + i);
			assertEquals(e.getDescEvent(),"discription" + i);
			assertEquals(e.getStartDatePickerEvent(),date.plusDays(i));
		}
	}
}
