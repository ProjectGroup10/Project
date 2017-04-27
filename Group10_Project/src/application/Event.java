package application.Group10_Project.src.application;


import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;

/**
 * @role This class represent the event itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public  class Event {

	private int id = 0 ;
	private String TitleEvent;
	private String DescEvent;
	private DatePicker StartDatePickerEvent;
	private DatePicker EndDatePickerEvent;
	private Image imageEvent;
	private boolean duration ; 
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
	 * @return StartDatePickerEvent
	 */
	public DatePicker getStartDatePickerEvent() {
		return StartDatePickerEvent;
	}

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

	/**
	 * Getter
	 * @return series
	 */
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
	
}
