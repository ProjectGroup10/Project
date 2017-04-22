package application;


import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;

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
		this.series = new XYChart.Series<String, Number>();
		//this.imageEvent = imageEvent;
	}

	
	public XYChart.Series<String, Number> getSeries() {
		return series;
	}

	public void setSeries(XYChart.Series<String, Number> series) {
		this.series = series;
	}

	public String getTitleEvent() {
		return TitleEvent;
	}

	public void setTitleEvent(String titleEvent) {
		TitleEvent = titleEvent;
	}

	public String getDescEvent() {
		return DescEvent;
	}

	public void setDescEvent(String descEvent) {
		DescEvent = descEvent;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDuration() {
		return duration;
	}

	public void setDuration(boolean duration) {
		this.duration = duration;
	}

	public DatePicker getStartDatePickerEvent() {
		return StartDatePickerEvent;
	}

	public void setStartDatePickerEvent(DatePicker startDatePickerEvent) {
		StartDatePickerEvent = startDatePickerEvent;
	}

	public DatePicker getEndDatePickerEvent() {
		return EndDatePickerEvent;
	}

	public void setEndDatePickerEvent(DatePicker endDatePickerEvent) {
		EndDatePickerEvent = endDatePickerEvent;
	}

	public Image getImageEvent() {
		return imageEvent;
	}

	public void setImageEvent(Image imageEvent) {
		this.imageEvent = imageEvent;
	}
	
}
