package application;

import org.json.simple.JSONArray;

import javafx.scene.control.DatePicker;

/**
 * @role This class represent the Timeline itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class Timeline 
{

	private int id = 0 ;
	private String title ;
	private DatePicker startDate ;
	private DatePicker endDate ;

	public Timeline (String title, DatePicker startTime, DatePicker endTime)
	{
		this.id = id++ ;
		this.title = title ;
		this.startDate = startTime ;
		this.endDate = endTime ;
	}
	/**
	 * Getter 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * setter
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Getter 
	 * @return startDate
	 */
	public DatePicker getStartTime() {
		return startDate;
	}
	/**
	 * setter
	 * @param startTime
	 */
	public void setStartTime(DatePicker startTime) {
		this.startDate = startTime;
	}
	/**
	 * Getter 
	 * @return endDate
	 */
	public DatePicker getEndTime() {
		return endDate;
	}
	/**
	 * setter
	 * @param endTime
	 */
	public void setEndTime(DatePicker endTime) {
		this.endDate = endTime;
	}
}
