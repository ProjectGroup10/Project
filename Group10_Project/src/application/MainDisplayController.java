package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        	// Get the JSONObject
        	JSONObject timeline = (JSONObject) o;
			String TitleTimeline = (String) timeline.get("TitleTimeline");
			String StartDate = (String) timeline.get("StartDateTimeline");
			String EndDate = (String) timeline.get("EndDateTimeline");

			// Create the DatePicker
			DatePicker StartDateTimeline = new DatePicker(LocalDate.parse(StartDate));
			DatePicker EndDateTimeline = new DatePicker(LocalDate.parse(EndDate));
			// Create the timeline with the previous informations
			Time t = new Time(TitleTimeline,StartDateTimeline,EndDateTimeline);
			// add the timeline
			StackPane root = new StackPane();
		        // add the lineChart to the StackPane
		     root.getChildren().add(t.getLineChart());
		       
		        
		       vbox.getChildren().add(root);			// Json array events
	        JSONArray EventCollection = (JSONArray) timeline.get("Events");
	        // Foreach each events object
	        for(Object e : EventCollection)
	        {
	        	JSONObject event = (JSONObject) e;
	        	String TitleEvent = (String) event.get("TitleEvent");
				String StartDateEvent = (String) event.get("StartDateEvent");
				String EndDateEvent = (String) event.get("EndDateEvent");
				DatePicker StartDateNewEvent = new DatePicker(LocalDate.parse(StartDateEvent));
				DatePicker EndDateNewEvent = new DatePicker(LocalDate.parse(EndDateEvent));
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
	        	// create an event 
	        	Event newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent,EndDateNewEvent,duration,image,imagetype);
	        	// call the addEvent method from the class Timeline
	        	t.addEvent(newEvent);

	        }   
         }
	}
	
	public class Time
	{
		private String title ; 
		private DatePicker startDate ; 
		private DatePicker endDate ;
		// List of events
		private ArrayList<Event> listEvent ;
		// LineChart who represents the "graphic timeline"
	    private LineChart<String,Number> lineChart ;
	
		public Time (String title, DatePicker startTime, DatePicker endTime)
		{
			//autoincrement id when creating a timeline
			this.title = title ;
			this.startDate = startTime ;
			this.endDate = endTime ;
			this.listEvent = new ArrayList<Event>() ;
			// method to initialize the line chart of the corresponding timeline
			initLineChart();
			
		}
		

		/**
		 * Getter 
		 * @return startDate
		 */
		public DatePicker getStartDate() {
			return startDate;
		}


		/**
		 * setter
		 * @param startDate
		 */
		public void setStartDate(DatePicker startDate) {
			this.startDate = startDate;
		}


		/**
		 * Getter 
		 * @return endDate
		 */
		public DatePicker getEndDate() {
			return endDate;
		}

		/**
		 * setter
		 * @param endDate
		 */
		public void setEndDate(DatePicker endDate) {
			this.endDate = endDate;
		}

		/**
		 * Getter 
		 * @return lineChart
		 */
		public LineChart<String, Number> getLineChart() {
			return lineChart;
		}

		/**
		 * setter
		 * @param lineChart
		 */
		public void setLineChart(LineChart<String, Number> lineChart) {
			this.lineChart = lineChart;
		}
				
		/**
		 * Getter 
		 * @return title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * setter
		 * @param title
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		
		/**
		 * @role this method intialize the lineCart depending on the startDate and the endDate
		 */
		private void initLineChart() 
		{
			String startDateTimeline = startDate.getValue().toString();
	    	int startYear = startDate.getValue().getYear();
	    	String endDateTimeline = endDate.getValue().toString();
	    	int endYear = endDate.getValue().getYear();
	    	
	    	// Used to define the size of the line chart
	    	int diffyear = endYear-startYear ;
	    	
	    	// representing the month
	    	ObservableList<String> months = FXCollections.observableArrayList() ;
	    	int date ;
	    	for (int i = 0; i <= diffyear ; i++)
	    	{
	    		date = startDate.getValue().getYear()+i ; 
	        	months.addAll("Jan "+ Integer.toString(date),"Feb "+ Integer.toString(date),"Mar "+ Integer.toString(date)
	        	,"Apr "+ Integer.toString(date),"May "+ Integer.toString(date),"Jun "+ Integer.toString(date),
	        	"Jul "+ Integer.toString(date),"Aug "+ Integer.toString(date),"Sep "+ Integer.toString(date),
	        	"Oct "+ Integer.toString(date),"Nov "+ Integer.toString(date),"Dec "+ Integer.toString(date));
	    	}
	    	// xAxis 
	        CategoryAxis xAxis = new CategoryAxis(months);
	        xAxis.setLabel("Months (Years)");
	        xAxis.setTickLabelRotation(90);
	        
	        // yAxis represente the days
	        NumberAxis yAxis = new NumberAxis(0,31,5);
	        yAxis.setLabel("Days");
	        
	        lineChart = new LineChart<String, Number>(xAxis, yAxis);
	        lineChart.setTitle(title + " (From " + startDateTimeline + " to " + endDateTimeline + ")");
	        lineChart.setMinHeight(450);
	        lineChart.setMinWidth((diffyear+1) * 500);
		}

		/**
		 * @role method addEvent who permit to add an event to the line chart, and to the list of 
		 * event corresponding at the special timeline
		 * @param event
		 */
		public void addEvent(Event event)
		{
			// add the event to the list of event 
	    	listEvent.add(event);
	    	
	    	String StartDate = event.getStartDatePickerEvent().getValue().toString();
	    	int startYear = event.getStartDatePickerEvent().getValue().getYear();
	    	int startMonth = event.getStartDatePickerEvent().getValue().getMonthValue();
	    	int startDay = event.getStartDatePickerEvent().getValue().getDayOfMonth();
	    	// call the method chooseMonth depending on which month is selected
	    	String monthStart = chooseMonth(startMonth); 
	    	String axisXstart = monthStart + startYear ;
		    
	    	// if it's a duration then add an endDate
			if(event.isDuration())
			{
		    	int endDay = event.getEndDatePickerEvent().getValue().getDayOfMonth();
		    	int endMonth = event.getEndDatePickerEvent().getValue().getMonthValue();

		    	int endYear = event.getEndDatePickerEvent().getValue().getYear();
				String monthEnd = chooseMonth(endMonth); 
		    	String axisXend = monthEnd + endYear ;

		    	// add the event to the lineChart, using a series from the class event
		        event.getSeries().setName(event.getTitleEvent());
		        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
		        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay));
			}
			else // it's a non duration event
			{
				event.getSeries().setName(event.getTitleEvent());
	    		event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
	    		
			}
			
			// add the series to the lineChart
	        lineChart.getData().add( event.getSeries());
	        

	        // for each data for a series
	        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
	        {
	        	// when clicking on the event 
	        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
		    	{
	        		public void handle(MouseEvent e) 
					{
	        			
					    Label text = null;
					    Image im;
					    if(event.getImageEvent() != null)
					    {
						    im = SwingFXUtils.toFXImage(event.getImageEvent(), null);

					    }
					    else
					    {
					    	im = null;
					    }
					    ImageView image = new ImageView();
					    image.setImage(im);
					    image.setFitHeight(40);
					    image.setFitWidth(40);
					    // if the event is a duration event
					    if(event.isDuration())
						{
							text = new Label("Title Event :  " + event.getTitleEvent() + "\n" +
									"Start date event : " + StartDate + "\n" + 
									"End date event : " + event.getEndDatePickerEvent().getValue().toString() + "\n" + 
									"Description : " + event.getDescEvent() );
						}
						else
						{
							text = new Label("Title Event :  " + event.getTitleEvent() + "\n" +
									"Start date event : " + StartDate + "\n" + 
									"Description : " + event.getDescEvent() );
						}
					 // display a new window with the information of the event 
					    Stage stage = new Stage();
				        stage.setTitle("Information event");
					    GridPane gp = new GridPane();
					    ScrollPane sp = new ScrollPane();
					    gp.setPadding(new Insets(10,10,10,10));
						gp.setVgap(5);
						gp.setHgap(10);
					    gp.add(image, 0, 0);
					    gp.add(text, 0, 1);
					    sp.setContent(gp);
					    sp.setPrefSize(300, 150);
					    VBox v = new VBox();
				        v.getChildren().add(sp);

					    Scene scene = new Scene(v, 300, 200);
					    stage.setScene(scene);
					    stage.show();
					}});
	        }	
		}
		
		
		/**
		 * @role method who "transform" the integer passed in parameters into the corresponding 
		 * month
		 * @param m
		 * @return month (string)
		 */
		
		public String chooseMonth(int m)
		{
			String month = "" ;
			// depending on which integer 
			switch(m)
	    	{
	    		case 1 : month = "Jan " ;
	    			break ;
	    		case 2 : month = "Feb " ;
	    			break ;
	    		case 3 : month = "Mar " ;
	    			break ;
	    		case 4 : month = "Apr " ;
	    			break ;
	    		case 5 : month = "May " ;
	    			break ;
	    		case 6 : month = "Jun " ;
	    			break ;
	    		case 7 : month = "Jul " ;
	    			break ;
	    		case 8 : month = "Aug " ;
	    			break ;
	    		case 9 : month = "Sep " ;
	    			break ;
	    		case 10 : month = "Oct " ;
	    			break ;
	    		case 11 : month = "Nov " ;
	    			break ;
	    		case 12 : month = "Dec " ;
	    	}
			return month ;
		}
	}
}