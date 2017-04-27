package application;

import java.time.LocalDate;

import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
/**
 * @role This class represent the event itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class Event {

	private int id = 0 ;
	private String title ;
	private String description ; 
	private LocalDate startTime ;
	private boolean duration ; 
	private LocalDate endTime ;
	private Image imageview;
	private XYChart.Series<String, Number> series ;

	
	public Event (String title, String description, LocalDate startTime, boolean duration, LocalDate endTime,Image view)
	{
		setTitle(title);
		setDescription(description);
		setStartTime(startTime);
		setDuration(duration);
		setEndTime(endTime);
		setImage(view);
		this.series = new XYChart.Series<String, Number>();

		
	}
	/**
	 * Getter
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter
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
	 * Setter
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Getter
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Setter
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Getter
	 * @return startTime
	 */
	public LocalDate getStartTime() {
		return startTime;
	}
	/**
	 * Setter
	 * @param startTime
	 */
	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}
	/**
	 * Getter
	 * @return duration
	 */
	public boolean isDuration() {
		return duration;
	}
	/**
	 * Setter
	 * @param duration
	 */
	public void setDuration(boolean duration) {
		this.duration = duration;
	}
	/**
	 * Getter
	 * @return endTime
	 */
	public LocalDate getEndTime() {
		return endTime;
	}
	/**
	 * Setter
	 * @param endTime
	 */

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}
	/**
	 * Getter
	 * @return i
	 */
	public Image getImage() {
		return imageview;
	}
	/**
	 * Setter
	 * @param i
	 */
	public void setImage(Image i) {
		this.imageview = i;
	}
	
	/**
	 * Getter
	 * @return series
	 */
	public XYChart.Series<String, Number> getSeries() {
		return series;
	}
	/**
	 * Setter
	 * @param series
	 */
	public void setSeries(XYChart.Series<String, Number> series) {
		this.series = series;
	}

}
