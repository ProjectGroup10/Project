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

import org.json.JSONArray;
import org.json.JSONException;

import org.json.simple.JSONObject;
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
            	System.out.println("Timeline added : " + titleTimeline);
            	TimelineCollection.add(timeline);
            	for(Timeline ti : TimelineCollection)
            	{
                	System.out.println("Timeline added : " + ti.getTitle());

            	}
            	addTimeline(timeline);

            	stage.close();
            }
        });  
	}
	
	
	public void addTimeline(Timeline t) 
	{
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
		for(Timeline ti : TimelineCollection)
    	{
			System.out.println("IN THE SAVE MODE PUTEEUH");
        	System.out.println("Timeline added : " + ti.getTitle());

    	}
		
		
		JSONObject obj = new JSONObject();
		int i = 0 ;
		int j = 0 ;
		for (Timeline t : TimelineCollection)
		{
			JSONObject timeline = new JSONObject();

			j++ ;
			timeline.put("TitleTimeline" + j, t.getTitle());
			timeline.put("StartDateTimeline" + j, t.getStartDate().getValue().toString());
			timeline.put("EndDateTimeline" + j,  t.getEndDate().getValue().toString());

			for(Event e : t.getListEvent())
			{
				JSONObject event = new JSONObject();

				i++ ;
				event.put("TitleEvent " + i, e.getTitleEvent());
				event.put("DescEvent" + i, e.getDescEvent());
				event.put("Duration" + i, e.isDuration());
				event.put("StartDateEvent" + i, e.getStartDatePickerEvent().getValue().toString());
				event.put("EndDateEvent" + i,  e.getEndDatePickerEvent().getValue().toString());
				timeline.put("Event(s)" + i, event);
			}
			obj.put("Timeline(s)" + j, timeline);
		}
		//datepicker.init(year, month, day, null);

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
         JSONObject TimelineCollection = (JSONObject) content.get("Timeline(s)");

         String array = "[" + content.toString() + "]" ;
         
         JSONArray jArray = new JSONArray(array);

         
         
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
		



