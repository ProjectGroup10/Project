package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;


import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
 * 
 */
public class MainController  {

	// List of the timelines
	public ArrayList<Timeline> TimelineCollection  =  new ArrayList<Timeline>();
	// VBox who contains all the timelines
	@FXML VBox vBoxModules;
	
	/**
	 * @role method called when clicking on the button add timeline, create a new window to 
	 * display a form where you can fill the informations about the timeline
	 */
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline"); // set the title of the window
        GridPane GP = new GridPane();
	    Scene scene = new Scene(GP, 320, 180);
	    stage.setScene(scene);
	    String  style= getClass().getResource("application.css").toExternalForm(); //link to css file
	    scene.getStylesheets().add(style);
	    GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		
	    DatePicker startDateTimeline = new DatePicker(); 
	    //startDateTimeline.setValue(LocalDate.now());

	    DatePicker endDateTimeline = new DatePicker();
    	endDateTimeline.setDisable(true);
	    

        TextField name = new TextField();
        Button submit = new Button("Submit");
        submit.setPrefSize(120, 40);
        // method to handle the fact that the end date should be after the start date
        setDatePicker(startDateTimeline,endDateTimeline); 
        
        GP.add(new Label("Title: "), 0, 0);
		GP.add(name, 1, 0);
		GP.add(new Label("Start Date: "), 0, 1 );
	    GP.add(startDateTimeline, 1, 1 );
	    GP.add(new Label("End Date: "), 0, 2 );
	    GP.add(endDateTimeline, 1, 2 );

	    Label label = new Label();
		label.setFont(new Font("Sans Serif",15));
		label.setTextFill(Color.RED);
		GP.add(label, 1, 5);
		GP.add(submit,1,6);
	    
	    stage.show();
	    
	    
	    startDateTimeline.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent arg0) 
			{
		    	endDateTimeline.setDisable(false);
			}	
	    });
	    
	    
	    
	    // Listener when submitting
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	// Test if the user enter all informations
            	if (!fill_value(name,startDateTimeline,endDateTimeline) && check_date(startDateTimeline,endDateTimeline)) 
            	{
            		// get the name of the timeline
            		String titleTimeline = name.getText(); 
            		Timeline timeline ;
                	// Creation of the new Timeline
            		if(isYearTimeline(startDateTimeline,endDateTimeline))
            		{
            			timeline =  new YearTimeline(titleTimeline,startDateTimeline,endDateTimeline);
                    	addTimeline(timeline);

            		}
            		else if(isMonthTimeline(startDateTimeline,endDateTimeline))
            		{
            			timeline = new MonthTimeline(titleTimeline,startDateTimeline,endDateTimeline);
                    	addTimeline(timeline);
            		}
            		else
            		{
            			timeline = new DayTimeline(titleTimeline,startDateTimeline,endDateTimeline);
                    	addTimeline(timeline);
            		}
                	
                	// method addTimeline called
                	stage.close();
    			}
            	else
            	{
            		label.setText("Title cannot be null!");
            	}
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

        MenuButton menuButton = new MenuButton("Options");

        //
        //
        menuButton.getItems().addAll(DeleteTimeLine, AddEvent);
        

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
            	Alert alert = new Alert(AlertType.CONFIRMATION);
            	alert.setTitle("Confirmation Dialog");
            	alert.setContentText("Are you sure?");

            	Optional<ButtonType> result = alert.showAndWait();
            
            	if (result.get() == ButtonType.OK){
            		// Remove the menu button corresponding to the timeline
                	vBoxModules.getChildren().remove(menuButton);
                	// Remove the HBox who contains the timeline
                	vBoxModules.getChildren().remove(h);	
                	// Timeline removed from the list TimelineCollection
                	TimelineCollection.remove(t);
            	
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
				
				// set the image
				String imageString = null;
				if(e.getImageEvent() != null&& (!e.getImageType().equals("")))//make sure photo not null
				{
					//find the type of image
					if(e.getImageType().endsWith("png"))
					{
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						ImageIO.write(e.getImageEvent(), "png", bos);
						byte[] phots= bos.toByteArray();
						imageString = Base64.getEncoder().encodeToString(phots).toString();
					}
					else if(e.getImageType().endsWith("jpg"))
					{
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						ImageIO.write(e.getImageEvent(), "jpg", bos);
						byte[] phots= bos.toByteArray();
						imageString = Base64.getEncoder().encodeToString(phots).toString();
					}
				}
				else
				{
					imageString = " ";
				}
				
				// add the attributes of a event into the JsonObject event
				eventObject.put("TitleEvent" , e.getTitleEvent());
				eventObject.put("DescEvent" , e.getDescEvent());
				eventObject.put("Duration" , e.isDuration());
				eventObject.put("StartDateEvent", e.getStartDatePickerEvent().getValue().toString());
				if(e.isDuration())
					eventObject.put("EndDateEvent",  e.getEndDatePickerEvent().getValue().toString());
				eventObject.put("Photos", imageString);
				eventObject.put("ImageType", e.getImageType());
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
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file or replace exists file");

        FileChooser.ExtensionFilter extFilterjson =  new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilterjson);
		File file = fileChooser.showSaveDialog(null);

	    if(file != null)
	    {
		    FileWriter fw = new FileWriter(file); 
	    	fw.write(allTimelines.toString());
	 	    fw.flush();
	 	    fw.close();
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
        // FileChoose used to choose the which file you want to load
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilterjson = new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().addAll(extFilterjson);
		File file = fileChooser.showOpenDialog(null);
	    reader=new FileReader(file);  


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
			String EndDate = (String) timeline.get("EndDateTimeline");

			// Create the DatePicker
			DatePicker StartDateTimeline = new DatePicker(LocalDate.parse(StartDate));
			DatePicker EndDateTimeline = new DatePicker(LocalDate.parse(EndDate));

			// Create the timeline with the previous informations
			
			Timeline t ;

			if(isYearTimeline(StartDateTimeline,EndDateTimeline))
    		{
    			t =  new YearTimeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
    		}
    		else if(isMonthTimeline(StartDateTimeline,EndDateTimeline))
    		{
    			t = new MonthTimeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
    		}
    		else
    		{
    			t = new DayTimeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
    		}
        	addTimeline(t);
        	 

			// Json array events
	        JSONArray EventCollection = (JSONArray) timeline.get("Events");
	        // Foreach each events object
	        for(Object e : EventCollection)
	        {
	        	JSONObject event = (JSONObject) e;
	        	String TitleEvent = (String) event.get("TitleEvent");
				String StartDateEvent = (String) event.get("StartDateEvent");

	        	String DescEvent = (String) event.get("DescEvent");
	        	boolean duration = (boolean) event.get("Duration");
	        	Event newEvent = null;
	        	
				DatePicker StartDateNewEvent = new DatePicker(LocalDate.parse(StartDateEvent));
	        	String imagetype = (String) event.get("ImageType");
	        	String photos = (String) event.get("Photos");
	        	BufferedImage image;
	        	if(!photos.equals(" "))
	        	{
	        		byte[] photosByte = Base64.getDecoder().decode(photos);
		        	ByteArrayInputStream in = new ByteArrayInputStream(photosByte); 
		        	image = ImageIO.read(in);
	        	}
	        	else
	        	{
	        		image = null;
	        	}

	        	if(duration)
	        	{
					String EndDateEvent = (String) event.get("EndDateEvent");
					DatePicker EndDateNewEvent = new DatePicker(LocalDate.parse(EndDateEvent));
		        	newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent,EndDateNewEvent,duration,image,imagetype);
	        	}
	        	else if(!duration)
	        	{

		        	newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent,duration,image,imagetype);
	        	}
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
			            if (item.isBefore(startDateTimeline.getValue().plusDays(1))) 
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

	protected boolean fill_value(TextField name, DatePicker startDateTimeline, DatePicker endDateTimeline) 
	{
		if (name.getText() == null || name.getText().trim().isEmpty()) 
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please give a name to your timeline!");
			alert.showAndWait();
			return true ;
		}
		if(startDateTimeline.getValue()==null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please select the start date");
			alert.showAndWait();
			return true ;
		}
		if(endDateTimeline.getValue()==null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please select the end date");
			alert.showAndWait();
			return true ;
		}
		
		return false;
	}

	protected boolean check_date(DatePicker startDateTimeline, DatePicker endDateTimeline) 
	{
		LocalDate start = startDateTimeline.getValue();
		LocalDate end = endDateTimeline.getValue();
		
		if(end.isEqual(start) || end.isBefore(start))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please check the date of you pick!");
			alert.showAndWait();
			return false ;
		}
		return true;
	}

	private boolean isYearTimeline(DatePicker startDateTimeline, DatePicker endDateTimeline)
	{
		String[] splitStartDateTimeline = startDateTimeline.getValue().toString().split("-");
		int startYear = Integer.parseInt(splitStartDateTimeline[0]);

		String[] splitEndDateTimeline = endDateTimeline.getValue().toString().split("-");
		int endYear = Integer.parseInt(splitEndDateTimeline[0]);

		if(startYear != endYear) 
			return true ;
		else
			return false ;
	}
	
	private boolean isMonthTimeline(DatePicker startDateTimeline, DatePicker endDateTimeline)
	{
		String[] splitStartDateTimeline = startDateTimeline.getValue().toString().split("-");
		int startYear = Integer.parseInt(splitStartDateTimeline[0]);
		int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
		String[] splitEndDateTimeline = endDateTimeline.getValue().toString().split("-");
		int endYear = Integer.parseInt(splitEndDateTimeline[0]);
		int endMonth = Integer.parseInt(splitEndDateTimeline[1]);

		if(startYear == endYear  && startMonth != endMonth) 
			return true ;
		else
			return false ;
	}
	
	/*
	private boolean isDayTimeline(DatePicker startDateTimeline, DatePicker endDateTimeline)
	{
		String[] splitStartDateTimeline = startDateTimeline.getValue().toString().split("-");
		int startYear = Integer.parseInt(splitStartDateTimeline[0]);
		int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
		String[] splitEndDateTimeline = endDateTimeline.getValue().toString().split("-");
		int endYear = Integer.parseInt(splitEndDateTimeline[0]);
		int endMonth = Integer.parseInt(splitEndDateTimeline[1]);


		if(startYear == endYear  && startMonth == endMonth ) 
			return true ;
		else
			return false ;
	}
	*/
}
		



