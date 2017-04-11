import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Timeline implements displayInterface
{

	private int id = 0 ;
	private String title ;
	private Date startTime ;
	private Date endTime ;
	private ArrayList<Event> listEvent ;
	
	public Timeline (String title, Date startTime, Date endTime)
	{
		this.id = id++ ;
		this.title = title ;
		this.startTime = startTime ;
		this.endTime = endTime ;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public ArrayList<Event> getListEvent() {
		return listEvent;
	}

	public void setListEvent(ArrayList<Event> listEvent) {
		this.listEvent = listEvent;
	}

	@Override
	public void display() {
		
		System.out.println("Title of timeline : " + title + "\n"+ 
							"Start time " + startTime.toString() + "\n" + 
							"End Time " + endTime.toString());	
		for (Event event : listEvent) 
		{
		    System.out.println("Title of event " + event.getTitle() + "\n" +
		    				   " Description " + event.getDescription() + "\n" +
		    					" Start Time " + event.getStartTime().getTime() + "\n");
		    if(event.isDuration())
		    {
		    	System.out.println("End Time" + event.getEndTime().getTime());
		    }
		}
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