import java.util.Date;

public class Event {

	private int id = 0 ;
	private String title ;
	private String description ; 
	private Date startTime ;
	private boolean duration ; 
	private Date endTime ;
	
	public Event (String title, String description, Date startTime, boolean duration, Date endTime)
	{
		this.id = id++ ;
		this.title = title ;
		this.description = description;
		this.startTime = startTime ;
		this.duration = duration ;
		this.endTime = endTime ;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public boolean isDuration() {
		return duration;
	}

	public void setDuration(boolean duration) {
		this.duration = duration;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public void modifyEvent(Event e1)
	{
		//set
	}

	
}
