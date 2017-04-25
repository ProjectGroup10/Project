package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.json.JSONException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @role This class is the controller class who handle the listeners for the buttons from the 
 * MainTemplate.fxml
 * @author Meng Li, Frapper Colin
 * @date 04/25/2017
 * @note : You have to include the Json packages to your java project to make it work, the 
 * org.json package and the org.simple.json package
 * 
 */
public class MainController  {

	// List of the timelines
	public ArrayList<Timeline> TimelineCollection  =  new ArrayList<Timeline>();
	// VBox who contains all the timelines
	@FXML VBox vBoxModules ;
	
	/**
	 * @role method called when clicking on the button add timeline, create a new window to 
	 * display a form where you can fill the informations about the timeline
	 */
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline"); // set the title of the window
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

	    DatePicker startDateTimeline = new DatePicker(); 
	    DatePicker endDateTimeline = new DatePicker();
        TextField name = new TextField();
        Button submit = new Button("Submit");
        // method to handle the fact that the end date should be after the start date
        setDatePicker(startDateTimeline,endDateTimeline); 
        
	    vbox.getChildren().addAll(new Label("Timeline name : "),name);
	    vbox.getChildren().addAll(new Label("Start Date : "),startDateTimeline);
	    vbox.getChildren().addAll(new Label("End Date : "),endDateTimeline);
	    vbox.getChildren().add(submit);

	    
	    stage.show();
	    
	    
	    // Listener when submitting
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	// get the name of the timeline
            	String titleTimeline = name.getText(); 
            	// Creation of the new Timeline
            	Timeline timeline = new Timeline(titleTimeline,startDateTimeline,endDateTimeline);
            	// method addTimeline called
            	addTimeline(timeline);

            	stage.close();
            }
        });  
	}
	
	/**
	 * @role : Method who permit to add a timeline to the arrayList and to display a menu button
	 * for this specific timeline, who contains the button add event or delete timeline
	 * @param t : Timeline
	 */
	public void addTimeline(Timeline t) 
	{
    	TimelineCollection.add(t);

    	// HBox who contains the timeline 
    	HBox h = new HBox();
    
    	// Menu button
        MenuItem DeleteTimeLine = new MenuItem("Delete Timeline");
        MenuItem AddEvent = new MenuItem("Add Event");

        MenuButton menuButton = new MenuButton("Options", null, DeleteTimeLine, AddEvent);
        
        vBoxModules.getChildren().add(menuButton);

        StackPane root = new StackPane();
        // add the lineChart to the StackPane
        root.getChildren().add(t.getLineChart());
       
        // add the StackPane to the Hbox
        h.getChildren().add(root);
        
        // Add the HBox to the VBox
        vBoxModules.getChildren().add(h);
       
        // Listener when clicking on add event
        AddEvent.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event) 
			{
				// method appearFormEvent in the class Timeline
				t.appearFormEvent();
			}
        	  
        });
        
        // Listener when clicking on delete timeline
        DeleteTimeLine.setOnAction(new EventHandler<ActionEvent>() 
	    {
            public void handle(ActionEvent e) 
            {
            	// Remove the menu button corresponding to the timeline
            	vBoxModules.getChildren().remove(menuButton);
            	// Remove the HBox who contains the timeline
            	vBoxModules.getChildren().remove(h);	
            	// Timeline removed from the list TimelineCollection
            	TimelineCollection.remove(t);
            }
	    });
	}
	

	
	@SuppressWarnings("unchecked")
	/**
	 * @role : method who permit to save the timelines into a Json File choose by the user
	 * @throws IOException
	 * @throws JSONException
	 */
	public void saveTimelines() throws IOException, JSONException
	{		
		
		JSONObject allTimelines = new JSONObject();
		JSONArray timelineArray = new JSONArray();

		// foreach all the timeline from the list of timeline
		for (Timeline t : TimelineCollection)
		{
			// for each timeline, a new Json object
			JSONObject timeline = new JSONObject();
			// creation of an array Events
			JSONArray events = new JSONArray();
			// add the attributes of a timeline into the JsonObject timeline
			timeline.put("TitleTimeline" , t.getTitle());
			timeline.put("StartDateTimeline", t.getStartDate().getValue().toString());
			timeline.put("EndDateTimeline",  t.getEndDate().getValue().toString());

			// foreach the events of a specific timeline from the list of events of this timeline
			for(Event e : t.getListEvent())
			{
				// for each event, a new Json object
				JSONObject eventObject = new JSONObject();
				
				// add the attributes of a event into the JsonObject event
				eventObject.put("TitleEvent" , e.getTitleEvent());
				eventObject.put("DescEvent" , e.getDescEvent());
				eventObject.put("Duration" , e.isDuration());
				eventObject.put("StartDateEvent", e.getStartDatePickerEvent().getValue().toString());
				eventObject.put("EndDateEvent",  e.getEndDatePickerEvent().getValue().toString());
				// add the event Json object to the Json event array 
				events.add(eventObject);
				// add the event Json array to the Json object timeline
				timeline.put("Events" ,events);

			}
			// add the timeline Json object to the Json timeline array
			timelineArray.add(timeline);

		}
		// add the timelines array to the Json object allTimeline
		allTimelines.put("Timelines", timelineArray);

		// JFileChoose use to permit to the user to save the timelines in his own file 
		JFileChooser chooser = new JFileChooser();
	    int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) 
	    {
	    	try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".json")) 
	    	{
	    	    fw.write(allTimelines.toString());
	    	}
	    }
	}
	
	
	@SuppressWarnings("resource")
	
	/**
	 * @role : Method who permit to load a JsonFile where you saved your timelines.
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONException
	 */
	public void loadTimelines() throws IOException, ParseException, JSONException
	{
		// Json Parser used to parse the Jsonfile
        JSONParser parser = new JSONParser();
        // File reader to read the file
        FileReader reader = null;
        // JFileChoose used to choose the which file you want to load
		JFileChooser jFileChooser=new JFileChooser();
	    int result= jFileChooser.showOpenDialog(null);
	    if(result==JFileChooser.APPROVE_OPTION)
	    {  
	    	File file=jFileChooser.getSelectedFile();
	    	reader=new FileReader(file);  
	    }	     
	    // Parse the reader 
        Object obj = parser.parse(reader);
        // Get the JsonObject
        JSONObject content = (JSONObject) obj;
        // get the array "Timelines" from the JsonObject
        JSONArray TimelineCollection = (JSONArray) content.get("Timelines");

        // for all object into the JsonArray
        for(Object o : TimelineCollection)
        {
        	// Get the JSONObject
        	JSONObject timeline = (JSONObject) o;
			String TitleTimeline = (String) timeline.get("TitleTimeline");
			String StartDate = (String) timeline.get("StartDateTimeline");
			// Split the string by - (example of StartDate 2017-02-25)
			String[] splitStartDateTimeline = StartDate.split("-");
			int startYear = Integer.parseInt(splitStartDateTimeline[0]);
			int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
			int startDay = Integer.parseInt(splitStartDateTimeline[2]);
			
			String EndDate = (String) timeline.get("EndDateTimeline");
			String[] splitEndDateTimeline = EndDate.split("-");
			int endYear = Integer.parseInt(splitEndDateTimeline[0]);
			int endMonth = Integer.parseInt(splitEndDateTimeline[1]);
			int endDay = Integer.parseInt(splitEndDateTimeline[2]);

			// Create the DatePicker
			DatePicker StartDateTimeline = new DatePicker(LocalDate.of(startYear, startMonth, startDay));
			DatePicker EndDateTimeline = new DatePicker(LocalDate.of(endYear, endMonth, endDay));
			// Create the timeline with the previous informations
			Timeline t = new Timeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
			// add the timeline
			addTimeline(t);
        	 
			// Json array events
	        JSONArray EventCollection = (JSONArray) timeline.get("Events");
	        
	        // Foreach each events object
	        for(Object e : EventCollection)
	        {
	        	JSONObject event = (JSONObject) e;
	        	String TitleEvent = (String) event.get("TitleEvent");
	        	
				String StartDateEvent = (String) event.get("StartDateEvent");

				String[] splitStartDateEvent = StartDateEvent.split("-");
				int startYearEvent = Integer.parseInt(splitStartDateEvent[0]);
				int startMonthEvent = Integer.parseInt(splitStartDateEvent[1]);
				int startDayEvent = Integer.parseInt(splitStartDateEvent[2]);
				String EndDateEvent = (String) event.get("EndDateEvent");
				String[] splitEndDateEvent = EndDateEvent.split("-");
				int endYearEvent = Integer.parseInt(splitEndDateEvent[0]);
				int endMonthEvent = Integer.parseInt(splitEndDateEvent[1]);
				int endDayEvent = Integer.parseInt(splitEndDateEvent[2]);

				DatePicker StartDateNewEvent = new DatePicker(LocalDate.of(startYearEvent, startMonthEvent, startDayEvent));
				DatePicker EndDateNewEvent = new DatePicker(LocalDate.of(endYearEvent, endMonthEvent, endDayEvent));

	        	String DescEvent = (String) event.get("DescEvent");
	        	boolean duration = (boolean) event.get("Duration");
	        	
	        	// create an event 
	        	Event newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent,EndDateNewEvent,duration);
	        	// call the addEvent method from the class Timeline
	        	t.addEvent(newEvent);

	        }   
         }
	}
	
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
	private void setDatePicker(DatePicker startDateTimeline,DatePicker endDateTimeline)
	{
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
		{
			public DateCell call(final DatePicker datePicker) 
			{
			    return new DateCell() 
			    {
			        public void updateItem(LocalDate item, boolean empty) 
			        {
			            super.updateItem(item, empty);
			            if (item.isBefore(startDateTimeline.getValue().plusDays(0))) 
			            {
			                    setDisable(true);
			                    setStyle("-fx-background-color: #ffc0cb;");
			            }
			            long days = ChronoUnit.DAYS.between(startDateTimeline.getValue(), item);
			            setTooltip(new Tooltip("You're choose " + days + " days"));
			        }
			    };
			}
		};
        endDateTimeline.setDayCellFactory(dayCellFactory);
	}
}
		



