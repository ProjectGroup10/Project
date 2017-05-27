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


/**
 * @role This class is the controller class who handle the listeners for the buttons from the 
 * DisplayMode.fxml
 * @author Meng Li, Frapper Colin
 * @date 05/23/2017
 * @note : You have to include the Json packages to your java project to make it work, the 
 * org.json package and the org.simple.json package
 */
public class MainDisplayController {
	
    @FXML VBox vbox;
    /**
     * @role : Method to display the timeline loaded in the display mode
     * @throws IOException
     * @throws ParseException
     * @throws JSONException
     */
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

	   if(file != null)
	   {
		   reader=new FileReader(file);  
		    // Parse the reader 
	        Object obj = parser.parse(reader);
	        // Get the JsonObject
	        JSONObject content = (JSONObject) obj;
	        // get the array "Timelines" from the JsonObject
	        JSONArray TimelineCollection = (JSONArray) content.get("Timelines");

	        if(TimelineCollection!=null)
	        {
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
	
					// depending on the date, check if it's a year, month or day timeline
					if(isYearTimeline(StartDateTimeline,EndDateTimeline))
		    		{
		    			t =  new YearTimeline(TitleTimeline,LocalDate.parse(StartDate),LocalDate.parse(EndDate));
		    		    t.initLineChart();
		    		}
		    		else if(isMonthTimeline(StartDateTimeline,EndDateTimeline))
		    		{
		    			t = new MonthTimeline(TitleTimeline,LocalDate.parse(StartDate),LocalDate.parse(EndDate));
		    		    t.initLineChart();
		    		}
		    		else
		    		{
		    			t = new DayTimeline(TitleTimeline,LocalDate.parse(StartDate),LocalDate.parse(EndDate));
		    		    t.initLineChart();
		    		}
					StackPane root = new StackPane();
				    // add the lineChart to the StackPane
				    root.getChildren().add(t.getLineChart());
			        h.getChildren().add(root);
	
				    vbox.getChildren().add(h);			
				    // Json array events
				        
				    // Json array events
			        JSONArray EventCollection = (JSONArray) timeline.get("Events");
			        
			        if(EventCollection!=null)
			        {
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
	
					        	newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent.getValue(),EndDateNewEvent.getValue(),duration,image,imagetype);
				        	}
				        	else if(!duration)
				        	{
	
					        	newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent.getValue(),duration,image,imagetype);
				        	}
				        	// call the displayEvent method from the class Timeline
				        	t.DisplayAddEvent(newEvent);
				        }   
		        	} 
	         	}
        	}
   		}

	}
	
	/**
	 * @role : Method to test if the timeline is a year timeline
	 * @param startDateTimeline
	 * @param endDateTimeline
	 * @return true if it's a year timeline, otherwise false
	 */
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
	
	/**
	 * @role : Method to test if the timeline is a month timeline
	 * @param startDateTimeline
	 * @param endDateTimeline
	 * @return true if it's a month timeline, otherwise false
	 */
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
	