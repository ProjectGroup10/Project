package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
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
 */
public class MainController  {

	// List of the timelines
	public ArrayList<Timeline> TimelineCollection  =  new ArrayList<Timeline>();
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
	 */
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
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
	    
	    // Listener when submitting
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
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
        // FileChooser used to choose the which file you want to load
        FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
	    reader=new FileReader(selectedFile);  
	    // Parse the reader 
        Object obj = parser.parse(reader);
        // Get the JsonObject
        JSONObject content = (JSONObject) obj;
        // get the array "Timelines" from the JsonObject
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
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
	private void setDatePicker()
	{
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                            if (item.isBefore(
	                            		startDatePicker.getValue().plusDays(0))
	                                ) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }
	                            long days = ChronoUnit.DAYS.between(
	                            		startDatePicker.getValue(), item
	                            );

	                            setTooltip(new Tooltip(
	                                "You're choose " + days + " days")
	                            );
	                            }
	                };
	            }
	        };
	        endDatePicker.setDayCellFactory(dayCellFactory);
	}
}
