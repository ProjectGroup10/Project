package application;


import java.awt.image.BufferedImage;
import java.time.LocalDate;

import javafx.scene.chart.XYChart;

/**
 * @role This class represent the event itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class Event {
	
	private int id = 0 ;
	private String TitleEvent;
	private String DescEvent;
	private LocalDate StartDatePickerEvent;
	private LocalDate EndDatePickerEvent;
	private BufferedImage imageEvent;
	private boolean duration ; 
	private String imageType;
	private XYChart.Series<String, Number> series ;

	// Constructor for a duration event 
	public Event (String TitleEvent, String DescEvent, LocalDate StartDatePickerEvent, LocalDate EndDatePickerEvent, boolean duration, BufferedImage imageEvent,String imagetype)
	{
		this.id = id++ ;
		this.TitleEvent = TitleEvent ;
		this.DescEvent = DescEvent;
		this.StartDatePickerEvent = StartDatePickerEvent ;
		this.duration = duration ;
		this.EndDatePickerEvent = EndDatePickerEvent ;
		this.imageEvent = imageEvent;
		// initialize the series when creating an event
		this.series = new XYChart.Series<String, Number>();
		this.imageType = imagetype;
	}
	
	// Constructor for an event without duration
	public Event (String TitleEvent, String DescEvent, LocalDate StartDatePickerEvent,  boolean duration, BufferedImage imageEvent,String imagetype)
	{
		this.id = id++ ;
		this.TitleEvent = TitleEvent ;
		this.DescEvent = DescEvent;
		this.StartDatePickerEvent = StartDatePickerEvent ;
		this.duration = duration ;
		this.imageEvent = imageEvent;
		// initialize the series when creating an event
		this.series = new XYChart.Series<String, Number>();
		this.imageType = imagetype;	
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
	public LocalDate getStartDatePickerEvent() {
		return StartDatePickerEvent;
	}

	/**
	 * Setter
	 * @param startDatePickerEvent
	 */
	public void setStartDatePickerEvent(LocalDate startDatePickerEvent) {
		StartDatePickerEvent = startDatePickerEvent;
	}

	/**
	 * Getter
	 * @return EndDatePickerEvent
	 */
	public LocalDate getEndDatePickerEvent() {
		return EndDatePickerEvent;
	}
	/**
	 * Setter
	 * @param endDatePickerEvent
	 */
	public void setEndDatePickerEvent(LocalDate endDatePickerEvent) {
		EndDatePickerEvent = endDatePickerEvent;
	}

	/**
	 * Getter
	 * @return imageEvent
	 */
	public BufferedImage getImageEvent() {
		return imageEvent;
	}

	/**
	 * Setter
	 * @param imageEvent
	 */
	public void setImageEvent(BufferedImage imageEvent) {
		this.imageEvent = imageEvent;
	}
	
	/**
	 * Getter
	 * @return imageEvent
	 */
	public String getImageType() {
		return this.imageType;
	}

	/**
	 * Setter
	 * @param imageEvent
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
}
