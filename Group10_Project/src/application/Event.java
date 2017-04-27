package application;


import javafx.scene.chart.XYChart;
<<<<<<< HEAD
import javafx.scene.image.Image;
=======
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
/**
 * @role This class represent the event itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
<<<<<<< HEAD
public class Event {
=======
public  class Event {
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3

	private int id = 0 ;
	private String TitleEvent;
	private String DescEvent;
	private DatePicker StartDatePickerEvent;
	private DatePicker EndDatePickerEvent;
	private Image imageEvent;
	private boolean duration ; 
<<<<<<< HEAD
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
=======
	private XYChart.Series<String, Number> series ;

	public Event (String TitleEvent, String DescEvent, DatePicker StartDatePickerEvent, DatePicker EndDatePickerEvent, boolean duration/*, Image imageEvent*/)
	{
		this.id = id++ ;
		this.TitleEvent = TitleEvent ;
		this.DescEvent = DescEvent;
		this.StartDatePickerEvent = StartDatePickerEvent ;
		this.duration = duration ;
		this.EndDatePickerEvent = EndDatePickerEvent ;
		// initialize the series when creating an event
		this.series = new XYChart.Series<String, Number>();
		//this.imageEvent = imageEvent;
	}
	public Event (String TitleEvent, String DescEvent, DatePicker StartDatePickerEvent,  boolean duration/*, Image imageEvent*/)
	{
		this.id = id++ ;
		this.TitleEvent = TitleEvent ;
		this.DescEvent = DescEvent;
		this.StartDatePickerEvent = StartDatePickerEvent ;
		this.duration = duration ;
		//this.EndDatePickerEvent = EndDatePickerEvent ;
		// initialize the series when creating an event
		this.series = new XYChart.Series<String, Number>();
		//this.imageEvent = imageEvent;
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

	/**
	 * Getter
	 * @return series
	 */
	public String getTitleEvent() {
		return TitleEvent;
	}

	/**
	 * Setter
	 * @param titleEvent
	 */
	public void setTitleEvent(String titleEvent) {
		TitleEvent = titleEvent;
	}

	/**
	 * Getter
	 * @return DescEvent
	 */
	public String getDescEvent() {
		return DescEvent;
	}

	/**
	 * Setter
	 * @param descEvent
	 */
	public void setDescEvent(String descEvent) {
		DescEvent = descEvent;
	}
	
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * Getter
	 * @return id
	 */
	public int getId() {
		return id;
	}
<<<<<<< HEAD
=======

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * Setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
<<<<<<< HEAD
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
=======

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * Getter
	 * @return duration
	 */
	public boolean isDuration() {
		return duration;
	}
<<<<<<< HEAD
=======

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * Setter
	 * @param duration
	 */
	public void setDuration(boolean duration) {
		this.duration = duration;
	}
<<<<<<< HEAD
	/**
	 * Getter
	 * @return endTime
	 */
	public LocalDate getEndTime() {
		return endTime;
=======

	/**
	 * Getter
	 * @return StartDatePickerEvent
	 */
	public DatePicker getStartDatePickerEvent() {
		return StartDatePickerEvent;
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	}
	/**
	 * Setter
	 * @param endTime
	 */

<<<<<<< HEAD
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
	
=======
	/**
	 * Setter
	 * @param startDatePickerEvent
	 */
	public void setStartDatePickerEvent(DatePicker startDatePickerEvent) {
		StartDatePickerEvent = startDatePickerEvent;
	}

	/**
	 * Getter
	 * @return EndDatePickerEvent
	 */
	public DatePicker getEndDatePickerEvent() {
		return EndDatePickerEvent;
	}
	/**
	 * Setter
	 * @param endDatePickerEvent
	 */
	public void setEndDatePickerEvent(DatePicker endDatePickerEvent) {
		EndDatePickerEvent = endDatePickerEvent;
	}

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * Getter
	 * @return series
	 */
<<<<<<< HEAD
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

=======
	public Image getImageEvent() {
		return imageEvent;
	}

	/**
	 * Setter
	 * @param imageEvent
	 */
	public void setImageEvent(Image imageEvent) {
		this.imageEvent = imageEvent;
	}
	
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
}
