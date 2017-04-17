package application;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Timeline 
{

	private int id = 0 ;
	private String title ;
	private DatePicker startDate ;
	private DatePicker endDate ;
	private ArrayList<Event> listEvent ;
    private ScatterChart<Number,Number> scatterChart ;

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

	public void display(VBox v) 
	{
		
		String StartDate = startDate.getValue().toString();
    	String[] splitStartDate = StartDate.split("-");
    	int startYear = Integer.parseInt(splitStartDate[0]);
    	String EndDate = endDate.getValue().toString();
    	String[] splitEndDate = EndDate.split("-");
    	int endYear = Integer.parseInt(splitEndDate[0]);
    	
    	HBox h = new HBox();
    	
    	int diffyear = endYear-startYear ;
    	
    	ObservableList<String> months = FXCollections.observableArrayList() ;
    	
    	for (int i = 0; i < diffyear ; i++)
    	{
    		int date = Integer.parseInt(splitStartDate[0])+i ; 
        	months.addAll("Jan "+ Integer.toString(date),"Feb "+ Integer.toString(date),"Mar "+ Integer.toString(date)
        	,"Apr "+ Integer.toString(date),"May "+ Integer.toString(date),"Jun "+ Integer.toString(date),
        	"Jul "+ Integer.toString(date),"Aug "+ Integer.toString(date),"Sep "+ Integer.toString(date),
        	"Oct "+ Integer.toString(date),"Nov "+ Integer.toString(date),"Dec "+ Integer.toString(date));
    	}
    	
        CategoryAxis xAxis = new CategoryAxis(months);
        xAxis.setLabel("Months (Years)");
        xAxis.setTickLabelRotation(90);
        
        
        NumberAxis yAxis = new NumberAxis(0,31,5);
        yAxis.setLabel("Days");
        
        ScatterChart<String,Number> scatterChart = new ScatterChart<String, Number>(xAxis, yAxis);
        scatterChart.setTitle(title);
        scatterChart.setMinHeight(450);
        scatterChart.setMinWidth(diffyear * 500);
      
	
        Button closeButton = new Button("Close all timelines");
       
        StackPane root = new StackPane();
        root.getChildren().add(scatterChart);
       
        h.getChildren().add(root);
	    
        v.getChildren().add(h);
       
        /*
        closeButton.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	v.getChildren().remove();
            }
	    });*/
        
        
		
		/*System.out.println("Title of timeline : " + title + "\n"+ 
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
		}*/
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