package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.json.JSONException;

import org.json.simple.JSONObject;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController implements Initializable {

	public ArrayList<Timeline> TimelineCollection  =  new ArrayList<Timeline>();
	@FXML VBox vBoxModules ;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

	    DatePicker startDateTimeline = new DatePicker();
	    DatePicker endDateTimeline = new DatePicker();
        TextField name = new TextField();
        Button submit = new Button("Submit");
        setDatePicker(startDateTimeline,endDateTimeline);
	    vbox.getChildren().addAll(new Label("Timeline name : "),name);
	    vbox.getChildren().addAll(new Label("Start Date : "),startDateTimeline);
	    vbox.getChildren().addAll(new Label("End Date : "),endDateTimeline);
	    vbox.getChildren().add(submit);

	    
	    stage.show();
	    
	    
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	String titleTimeline = name.getText();
            	Timeline timeline = new Timeline(titleTimeline,startDateTimeline,endDateTimeline);
            	
            	addTimeline(timeline);

            	stage.close();
            }
        });  
	}
	
	
	public void addTimeline(Timeline t) 
	{
    	TimelineCollection.add(t);

    	HBox h = new HBox();
    
   
        MenuItem DeleteTimeLine = new MenuItem("Delete Timeline");
        MenuItem AddEvent = new MenuItem("Add Event");

        MenuButton menuButton = new MenuButton("Options", null, DeleteTimeLine, AddEvent);
        
        vBoxModules.getChildren().add(menuButton);

        StackPane root = new StackPane();
        root.getChildren().add(t.getLineChart());
       
        h.getChildren().add(root);
        vBoxModules.getChildren().add(h);
       
        AddEvent.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) 
			{
				t.appearFormEvent();
			}
        	  
        });
        
        DeleteTimeLine.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	vBoxModules.getChildren().remove(menuButton);
            	vBoxModules.getChildren().remove(h);	
            	TimelineCollection.remove(t);
            }
	    });
	}
	
	
	private void setDatePicker(DatePicker startDateTimeline,DatePicker endDateTimeline  )
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
	
	public void saveTimelines() throws IOException, JSONException
	{		
		
		JSONObject obj = new JSONObject();
		JSONArray timelineArray = new JSONArray();

		for (Timeline t : TimelineCollection)
		{
			JSONObject timeline = new JSONObject();
			JSONArray event = new JSONArray();
			
			timeline.put("TitleTimeline" , t.getTitle());
			timeline.put("StartDateTimeline", t.getStartDate().getValue().toString());
			timeline.put("EndDateTimeline",  t.getEndDate().getValue().toString());

			for(Event e : t.getListEvent())
			{
				JSONObject eventObject = new JSONObject();

				eventObject.put("TitleEvent" , e.getTitleEvent());
				eventObject.put("DescEvent" , e.getDescEvent());
				eventObject.put("Duration" , e.isDuration());
				eventObject.put("StartDateEvent", e.getStartDatePickerEvent().getValue().toString());
				eventObject.put("EndDateEvent",  e.getEndDatePickerEvent().getValue().toString());
				event.add(eventObject);
				timeline.put("Events" ,event);

			}
			timelineArray.add(timeline);

		}
		obj.put("Timelines", timelineArray);


		JFileChooser chooser = new JFileChooser();
	    //chooser.setCurrentDirectory(new File("/home/me/Documents"));
	    int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".json")) {
	    	    fw.write(obj.toString());
	    	    System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + obj);
	    	}
	    }
	}
	
	
	@SuppressWarnings("resource")
	public void loadTimelines() throws IOException, ParseException, JSONException
	{
        JSONParser parser = new JSONParser();
        FileReader reader = null;
        	    
		JFileChooser jFileChooser=new JFileChooser();
	    int result= jFileChooser.showOpenDialog(null);
	    if(result==JFileChooser.APPROVE_OPTION)
	    {  
	    	File file=jFileChooser.getSelectedFile();
	    	reader=new FileReader(file);  
	    }	     
        Object obj = parser.parse(reader);
        JSONObject content = (JSONObject) obj;
        JSONArray TimelineCollection = (JSONArray) content.get("Timelines");

        System.out.println(TimelineCollection.toString());     
        
        for(Object o : TimelineCollection)
        {
        	JSONObject timeline = (JSONObject) o;
        	 
			String TitleTimeline = (String) timeline.get("TitleTimeline");
			String StartDate = (String) timeline.get("StartDateTimeline");
			String[] splitStartDateTimeline = StartDate.split("-");
			int startYear = Integer.parseInt(splitStartDateTimeline[0]);
			int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
			int startDay = Integer.parseInt(splitStartDateTimeline[2]);
			String EndDate = (String) timeline.get("EndDateTimeline");
			String[] splitEndDateTimeline = EndDate.split("-");
			int endYear = Integer.parseInt(splitEndDateTimeline[0]);
			int endMonth = Integer.parseInt(splitEndDateTimeline[1]);
			int endDay = Integer.parseInt(splitEndDateTimeline[2]);

			
			DatePicker StartDateTimeline = new DatePicker(LocalDate.of(startYear, startMonth, startDay));
			DatePicker EndDateTimeline = new DatePicker(LocalDate.of(endYear, endMonth, endDay));
			Timeline t = new Timeline(TitleTimeline,StartDateTimeline,EndDateTimeline);
			addTimeline(t);
        	 
	        JSONArray EventCollection = (JSONArray) timeline.get("Events");
	        for(Object e : EventCollection)
	        {
	        	JSONObject event = (JSONObject) e;
	        	String TitleEvent = (String) event.get("TitleEvent");
	        	
				String StartDateEvent = (String) event.get("StartDateEvent");
				System.out.println(StartDateEvent);
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
	        	Event newEvent = new Event(TitleEvent,DescEvent,StartDateNewEvent,EndDateNewEvent,duration);
	        	t.addEvent(newEvent);

	        }   
         }
         
		/*
        try {

            Object obj = parser.parse(new FileReader("f:\\test.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }*/
	}


	
}
		



