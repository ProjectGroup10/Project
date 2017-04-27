package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Optional;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
=======

import javax.swing.JFileChooser;

import org.json.JSONException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
<<<<<<< HEAD
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
=======
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @role This class is the controller class who handle the listeners for the buttons from the 
 * MainTemplate.fxml
 * @author Meng Li, Frapper Colin
 * @date 04/25/2017
 * @note : You have to include the Json packages to your java project to make it work, the 
 * org.json package and the org.simple.json package
<<<<<<< HEAD
=======
 * 
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
 */
public class MainController  {

	// List of the timelines
	public ArrayList<Timeline> TimelineCollection  =  new ArrayList<Timeline>();
<<<<<<< HEAD
	// List of theDisplayTimeLines
	private ArrayList<DisplayTimeLine> Display  =  new ArrayList<DisplayTimeLine>();
	private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField name;
    private Timeline timeline;
    
	// VBox who contains all the timelines
	@FXML VBox vBoxModules ;

	/**
	 * @role method called when clicking on the button add timeline, create a new window to 
	 * display a form where you can fill the informations about the timeline 
=======
	// VBox who contains all the timelines
	@FXML VBox vBoxModules ;
	
	/**
	 * @role method called when clicking on the button add timeline, create a new window to 
	 * display a form where you can fill the informations about the timeline
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	 */
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
<<<<<<< HEAD
        stage.setTitle("Form timeline");
	    GridPane GP = new GridPane();
	    Scene scene = new Scene(GP, 300, 180);
	    stage.setScene(scene);

		GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		
        name = new TextField();
		GP.add(new Label("Title: "), 0, 0);
		GP.add(name, 1, 0);
		
		startDatePicker = new DatePicker();
	    startDatePicker.setValue(LocalDate.now());
	 
	    GP.add(new Label("Start Date: "), 0, 1 );
	    GP.add(startDatePicker, 1, 1 );
	    
	    endDatePicker = new DatePicker();
	    endDatePicker.setValue(LocalDate.now());
		GP.add(new Label("End Date: "), 0, 2 );
	    GP.add(endDatePicker, 1, 2 );
	    
        // method to handle the fact that the end date should be after the start date
        setDatePicker();

        Button submit = new Button("Submit");
        submit.setPrefSize(120, 40);
		Label label = new Label();
		label.setFont(new Font("Sans Serif",15));
		label.setTextFill(Color.RED);
		GP.add(label, 1, 5);
		GP.add(submit,1,6);
	    stage.show();
	    
=======
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
	    
	    
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	    // Listener when submitting
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
<<<<<<< HEAD
            	if(name.getText() != null && !name.getText().isEmpty() )
            	{
            		if(startDatePicker.getValue().getYear() > endDatePicker.getValue().getYear())
    				{
    					Alert alert = new Alert(AlertType.WARNING);
    					alert.setTitle("Warning");
    					alert.setHeaderText("Please check the date of you pick!");
    					alert.showAndWait();
    				}
    				else if(startDatePicker.getValue().getYear() == endDatePicker.getValue().getYear())
    				{
    					if(startDatePicker.getValue().getMonthValue() > endDatePicker.getValue().getMonthValue() )
    					{
    						Alert alert = new Alert(AlertType.WARNING);
    						alert.setTitle("Warning");
    						alert.setHeaderText("Please check the date of you pick!");
    						alert.showAndWait();
    					}
    					else if(startDatePicker.getValue().getMonthValue() == endDatePicker.getValue().getMonthValue())
    					{
    						if(startDatePicker.getValue().getDayOfMonth() > endDatePicker.getValue().getDayOfMonth())
    						{
    							Alert alert = new Alert(AlertType.WARNING);
    							alert.setTitle("Warning");
    							alert.setHeaderText("Please check the date of you pick!");
    							alert.showAndWait();
    						}
    						else
    						{
    							timeline = new Timeline(name.getText(),startDatePicker,endDatePicker);
    							TimelineCollection.add(timeline);
    							DisplayTimeLine dis = new DisplayTimeLine(timeline);
    			            	dis.display(vBoxModules);
    			                // Listener when clicking on delete timeline
    			            	dis.getDeleteitem().setOnAction(new EventHandler<ActionEvent>()
    			        		{

    			        			@Override
    			        			public void handle(ActionEvent event) {
    			        				// TODO Auto-generated method stub
    			        				Alert alert = new Alert(AlertType.CONFIRMATION);
    	    							alert.setTitle("Confirmation Dialog");
    	    							alert.setContentText("Confirm to delete event in timeline");
    	    							Optional<ButtonType> confirmation = alert.showAndWait();
    	    							if(confirmation.isPresent() && confirmation.get() == ButtonType.OK)
    	    							{
    	    				            	// Remove the menu button corresponding to the timeline
    	    								vBoxModules.getChildren().remove(dis.getMenuButoon());
    	    				            	// Remove the HBox who contains the timeline
    	    								vBoxModules.getChildren().remove(dis.getHbox());
    	    				            	// Timeline removed from the list TimelineCollection
    	    								TimelineCollection.remove(dis.getTime());
    	    							}
    			        			}
    			        	
    			        		});
    			            	Display.add(dis);
    			            	stage.close();
    						}
    					}
    					else
    					{
    						timeline = new Timeline(name.getText(),startDatePicker,endDatePicker);
    						TimelineCollection.add(timeline);
    						DisplayTimeLine dis = new DisplayTimeLine(timeline);
    		            	dis.display(vBoxModules);
    		                // Listener when clicking on delete timeline
    		            	dis.getDeleteitem().setOnAction(new EventHandler<ActionEvent>()
    		        		{

    		        			@Override
    		        			public void handle(ActionEvent event) {
    		        				// TODO Auto-generated method stub
    		        				Alert alert = new Alert(AlertType.CONFIRMATION);
        							alert.setTitle("Confirmation Dialog");
        							alert.setContentText("Confirm to delete event in timeline");
        							Optional<ButtonType> confirmation = alert.showAndWait();
        							if(confirmation.isPresent() && confirmation.get() == ButtonType.OK)
        							{
        				            	// Remove the menu button corresponding to the timeline
        								vBoxModules.getChildren().remove(dis.getMenuButoon());
        				            	// Remove the HBox who contains the timeline
        								vBoxModules.getChildren().remove(dis.getHbox());
        				            	// Timeline removed from the list TimelineCollection
        								TimelineCollection.remove(dis.getTime());
        							}
    		        		    }
    		        	
    		        		});
    		            	Display.add(dis);
    		            	stage.close();
    					}
    				}
    				else
    				{
    					timeline = new Timeline(name.getText(),startDatePicker,endDatePicker);
    					TimelineCollection.add(timeline);
    					DisplayTimeLine dis = new DisplayTimeLine(timeline);
    	            	dis.display(vBoxModules);
    	                // Listener when clicking on delete timeline
    	            	dis.getDeleteitem().setOnAction(new EventHandler<ActionEvent>()
    	        		{

    	        			@Override
    	        			public void handle(ActionEvent event) {
    	        				// TODO Auto-generated method stub
    	        				Alert alert = new Alert(AlertType.CONFIRMATION);
    							alert.setTitle("Confirmation Dialog");
    							alert.setContentText("Confirm to delete event in timeline");
    							Optional<ButtonType> confirmation = alert.showAndWait();
    							if(confirmation.isPresent() && confirmation.get() == ButtonType.OK)
    							{
    				            	// Remove the menu button corresponding to the timeline
    								vBoxModules.getChildren().remove(dis.getMenuButoon());
    				            	// Remove the HBox who contains the timeline
    								vBoxModules.getChildren().remove(dis.getHbox());
    				            	// Timeline removed from the list TimelineCollection
    								TimelineCollection.remove(dis.getTime());
    							}
    	        			}
    	        		});
    	            	Display.add(dis);
    	            	stage.close();
    				}
            	}
            	else
            	{
            		label.setText("Title cannot be null!");
            	}
=======
            	// get the name of the timeline
            	String titleTimeline = name.getText(); 
            	// Creation of the new Timeline
            	Timeline timeline = new Timeline(titleTimeline,startDateTimeline,endDateTimeline);
            	// method addTimeline called
            	addTimeline(timeline);

            	stage.close();
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
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
	
	
<<<<<<< HEAD
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
			
			// add the attributes of a timeline into the JsonObject timeline
			timeline.put("TitleTimeline" , t.getTitle());
			timeline.put("StartDateTimeline", t.getStartTime().getValue().toString());
			timeline.put("EndDateTimeline",  t.getEndTime().getValue().toString());
			
			// add the timeline Json object to the Json timeline array
			timelineArray.add(timeline);
			// creation of an array Events
			JSONArray eventArray = new JSONArray();
			//JSONArray events = new JSONArray();
			for(int i = 0; i < Display.size(); i++)
			{
				JSONObject eventObject = new JSONObject();
				DisplayTimeLine d = Display.get(i);
				for(Event e : d.getListEvent())
				{	
					
					// add the attributes of a event into the JsonObject event
					eventObject.put("ID", i);
					eventObject.put("TitleEvent" , e.getTitle());
					eventObject.put("DescEvent" , e.getDescription());
					eventObject.put("Duration" , e.isDuration());
					eventObject.put("StartDateEvent", e.getStartTime());
					eventObject.put("EndDateEvent",  e.getEndTime());
				}
				eventArray.add(eventObject);
				timeline.put("Event", eventArray);
			}
		}		
		// add the timelines and events array to the Json object allTimeline
		allTimelines.put("Timeline", timelineArray);
		
		// FileChooser use to permit to the user to save the timelines in his own file 
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		try(FileWriter fw = new FileWriter(selectedFile)) 
    	{
    	    fw.write(allTimelines.toString());
    	}
	    	
	}
=======
	@SuppressWarnings("resource")
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	
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
<<<<<<< HEAD
        // FileChooser used to choose the which file you want to load
        FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
	    reader=new FileReader(selectedFile);  
=======
        // JFileChoose used to choose the which file you want to load
		JFileChooser jFileChooser=new JFileChooser();
	    int result= jFileChooser.showOpenDialog(null);
	    if(result==JFileChooser.APPROVE_OPTION)
	    {  
	    	File file=jFileChooser.getSelectedFile();
	    	reader=new FileReader(file);  
	    }	     
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	    // Parse the reader 
        Object obj = parser.parse(reader);
        // Get the JsonObject
        JSONObject content = (JSONObject) obj;
        // get the array "Timelines" from the JsonObject
<<<<<<< HEAD
        JSONArray TimelineCollect = (JSONArray) content.get("Timeline");

        // for all object into the JsonArray
        for(Object o : TimelineCollect)
        {
        	System.out.println(o);
        	// Get the JSONObject
        	JSONObject timeline = (JSONObject) o;
			String TitleTimeline = (String) timeline.get("TitleTimeline");
			String StartDate = (String) timeline.get("StartDateTimeline");			
			String EndDate = (String) timeline.get("EndDateTimeline");
			
			// Create the DatePicker
			DatePicker StartDateTimeline = new DatePicker(LocalDate.parse(StartDate));
			DatePicker EndDateTimeline = new DatePicker(LocalDate.parse(EndDate));
			
			// Create the timeline with the previous informations
			Timeline t = new Timeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
			
			// add the timeline
			TimelineCollection.add(t);
			DisplayTimeLine dis = new DisplayTimeLine(t);
        	dis.display(vBoxModules);
        	dis.getDeleteitem().setOnAction(new EventHandler<ActionEvent>()
    		{

    			@Override
    			public void handle(ActionEvent event) {
    				// TODO Auto-generated method stub
    				Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setContentText("Confirm to delete event in timeline");
					Optional<ButtonType> confirmation = alert.showAndWait();
					if(confirmation.isPresent() && confirmation.get() == ButtonType.OK)
					{
						vBoxModules.getChildren().remove(dis.getMenuButoon());
						vBoxModules.getChildren().remove(dis.getHbox());
						TimelineCollection.remove(dis.getTime());
					}
    			}
    	
    		});
        	Display.add(dis);
        	
        	// Json array events
            JSONArray EventCollection = (JSONArray) timeline.get("Event");
            
            // Foreach each events object
            for(Object e : EventCollection)
            {
            	JSONObject event = (JSONObject) e;
            	int id = (int) event.get("ID");
            	String TitleEvent = (String) event.get("TitleEvent");
    			String StartDateEvent = (String) event.get("StartDateEvent");
    			String EndDateEvent = (String) event.get("EndDateEvent");
    			Image image = null;
            	String DescEvent = (String) event.get("DescEvent");
            	boolean duration = (boolean) event.get("Duration");
            	
            	// create an event 
            	Event newEvent = new Event(TitleEvent,DescEvent,LocalDate.parse(StartDateEvent),duration,LocalDate.parse(EndDateEvent),image);
            	// call the addEvent method from the class Timeline
            	Display.get(id).addEvent(newEvent);

            }   
            } 
			
     
        
	}
=======
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
	
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
<<<<<<< HEAD
	private void setDatePicker()
=======
	private void setDatePicker(DatePicker startDateTimeline,DatePicker endDateTimeline)
>>>>>>> 4b4ba56f4f9baca0daa96f917906e4d1f84e94e3
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
		



