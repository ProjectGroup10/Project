package application;

import java.time.LocalDate;

import javafx.scene.image.Image;

public class Event {

	private int id = 0 ;
	private String title ;
	private String description ; 
	private LocalDate startTime ;
	private boolean duration ; 
	private LocalDate endTime ;
	private Image imageview;
	
	public Event (String title, String description, LocalDate startTime, boolean duration, LocalDate endTime,Image view)
	{
		this.id = id++ ;
		setId(this.id);
		setTitle(title);
		setDescription(description);
		setStartTime(startTime);
		setDuration(duration);
		setEndTime(endTime);
		setImage(view);
		
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

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public boolean isDuration() {
		return duration;
	}

	public void setDuration(boolean duration) {
		this.duration = duration;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}
	
	public Image getImage() {
		return imageview;
	}

	public void setImage(Image i) {
		this.imageview = i;
	}

	public void modifyEvent(Event e1)
	{
		//set
	}

	
}
