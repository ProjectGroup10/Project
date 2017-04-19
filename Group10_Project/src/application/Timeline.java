package application;

import java.util.ArrayList;
import javafx.scene.control.DatePicker;


public class Timeline 
{

	private int id = 0 ;
	private String title ;
	private DatePicker startDate ;
	private DatePicker endDate ;
	private ArrayList<Event> listEvent ;


	public Timeline (String title, DatePicker startTime, DatePicker endTime)
	{
		this.id = id++ ;
		this.title = title ;
		this.startDate = startTime ;
		this.endDate = endTime ;
		this.listEvent = new ArrayList<Event>() ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DatePicker getStartTime() {
		return startDate;
	}

	public void setStartTime(DatePicker startTime) {
		this.startDate = startTime;
	}

	public DatePicker getEndTime() {
		return endDate;
	}

	public void setEndTime(DatePicker endTime) {
		this.endDate = endTime;
	}

	public ArrayList<Event> getListEvent() {
		return listEvent;
	}

	public void setListEvent(ArrayList<Event> listEvent) {
		this.listEvent = listEvent;
	}
	
		
	public void addTimeline(Event e)
	{
		if(validateTimeEvent(e))
		{
			listEvent.add(e);
		}
		else
		{
			System.out.println("Error about the date");
		}
	}
	
	public void deleteEvent(Event e)
	{
		listEvent.remove(e);
	}
	
	public void saveEvent()
	{
		// FILE READER 
	}
	
	public boolean validateTimeEvent(Event e)
	{
		if (e.getStartTime().compareTo(e.getEndTime()) < 0 )
		{
			return true ;
		}
		else
			return false ;
	}
	
	public void modifyEvent(Event e)
	{
		for (Event event : listEvent) 
		{	   
		    if(event.getId() == e.getId())
		    {
		    	event.setTitle(e.getTitle());
		    	event.setDescription(e.getDescription());
		    	event.setStartTime(e.getStartTime());
		    	if(e.isDuration())
		    	{
		    		event.setDuration(e.isDuration());
		    		event.setEndTime(e.getEndTime());
		    	}
		    	
		    }
		}
	}
}
