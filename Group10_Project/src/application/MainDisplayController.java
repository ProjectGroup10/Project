package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;



public class MainDisplayController {
	
    @FXML VBox vbox;
	public void DisplayTimeline() throws IOException, ParseException, JSONException
	{
		
		// Json Parser used to parse the Jsonfile
        JSONParser parser = new JSONParser();
        // File reader to read the file
        FileReader reader = null;
        // FileChooser used to choose the which file you want to load
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFiltertxt = 
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilterjson = 
                new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters()
                .addAll( extFiltertxt, extFilterjson);
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
        	HBox h = new HBox();
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
			// add the timeline
			StackPane root = new StackPane();
		    // add the lineChart to the StackPane
		    root.getChildren().add(t.getLineChart());
	        h.getChildren().add(root);

		    vbox.getChildren().add(h);			
		    // Json array events
		        
		       vbox.getChildren().add(root);			// Json array events
	        JSONArray EventCollection = (JSONArray) timeline.get("Events");
	        // Foreach each events object
	        for(Object e : EventCollection)
	        {
	        	JSONObject event = (JSONObject) e;
	        	String TitleEvent = (String) event.get("TitleEvent");
				String StartDateEvent = (String) event.get("StartDateEvent");
				DatePicker StartDateNewEvent = new DatePicker(LocalDate.parse(StartDateEvent));

	        	String DescEvent = (String) event.get("DescEvent");
	        	boolean duration = (boolean) event.get("Duration");
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
	        	Event newEvent = null ;

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
	        	// create an event 
	        	// create an event 
	        	// call the addEvent method from the class Timeline
	        	t.addEvent(newEvent);

	        }   
         }
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

		if(startYear==endYear && startMonth != endMonth) 
		{
			return true ;
		}
		else
			return false ;
	}
			
}
	