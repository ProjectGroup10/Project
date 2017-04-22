package application;
<<<<<<< HEAD
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
=======

import java.util.ArrayList;
import javafx.scene.control.DatePicker;

>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238

public class Timeline 
{

	private int id = 0 ; // id of the timeline 
	private String title ; // Title of the timeline
	private DatePicker startDate ; 
	private DatePicker endDate ;
	private ArrayList<Event> listEvent ;
<<<<<<< HEAD
    private LineChart<String,Number> lineChart ;
=======

>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238

	public Timeline (String title, DatePicker startTime, DatePicker endTime)
	{
		this.id = id++ ;
		this.title = title ;
		this.startDate = startTime ;
		this.endDate = endTime ;
		this.listEvent = new ArrayList<Event>() ;
		initLineChart();
		
	}
	

	private void initLineChart() 
	{
		String startDateTimeline = startDate.getValue().toString();
    	String[] splitStartDateTimeline = startDateTimeline.split("-");
    	int startYear = Integer.parseInt(splitStartDateTimeline[0]);
    	String endDateTimeline = endDate.getValue().toString();
    	String[] splitEndDateTimeline = endDateTimeline.split("-");
    	int endYear = Integer.parseInt(splitEndDateTimeline[0]);
    	
    	// Used to define the size of the line chart
    	int diffYear = endYear-startYear ;
    	
    	ObservableList<String> months = FXCollections.observableArrayList() ;

    	for (int i = 0; i <= diffYear ; i++)
    	{
    		startYear = Integer.parseInt(splitStartDateTimeline[0])+i ; 
        	months.addAll("Jan "+ Integer.toString(startYear),"Feb "+ Integer.toString(startYear),"Mar "+ Integer.toString(startYear)
        	,"Apr "+ Integer.toString(startYear),"May "+ Integer.toString(startYear),"Jun "+ Integer.toString(startYear),
        	"Jul "+ Integer.toString(startYear),"Aug "+ Integer.toString(startYear),"Sep "+ Integer.toString(startYear),
        	"Oct "+ Integer.toString(startYear),"Nov "+ Integer.toString(startYear),"Dec "+ Integer.toString(startYear));
    	}
    	
        CategoryAxis xAxis = new CategoryAxis(months);
        xAxis.setLabel("Months (Years)");
        xAxis.setTickLabelRotation(90);
        
        
        NumberAxis yAxis = new NumberAxis(0,31,5);
        yAxis.setLabel("Days");
        
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle(title + "From " + startDateTimeline + " to " + endDateTimeline);
        lineChart.setMinHeight(450);
        lineChart.setMinWidth((diffYear+1) * 500);
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
<<<<<<< HEAD

	public void display(VBox v) 
	{
    	HBox h = new HBox();

        MenuItem DeleteTimeLine = new MenuItem("Delete Timeline");
        MenuItem AddEvent = new MenuItem("Add Event");
        MenuItem ModifyEvent = new MenuItem("Modify Event");
        MenuItem DeleteEvent = new MenuItem("Delete Event");


        MenuButton menuButton = new MenuButton("Options", null, DeleteTimeLine, AddEvent , ModifyEvent, DeleteEvent);
        
       
        AddEvent.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) 
			{
				appearFormEvent();
			}
        	  
        });
        

        StackPane root = new StackPane();
        root.getChildren().add(lineChart);
        h.getChildren().add(menuButton);
        h.getChildren().addAll(root);
        v.getChildren().add(h);
       
        
        DeleteTimeLine.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	v.getChildren().remove(h);
            }
	    });
	}

	public void appearFormEvent()
	{
		Stage stage = new Stage();
        stage.setTitle("Add Event");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

        TextField nameEvent = new TextField();
	    DatePicker startDatePickerEvent = new DatePicker();
	    DatePicker endDatePickerEvent = new DatePicker();
	    TextField DescField = new TextField();
	   // Image image= new Image();
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        setDatePicker(startDatePickerEvent,endDatePickerEvent);

	    vbox.getChildren().addAll(new Text("Event name : "),nameEvent);
	    vbox.getChildren().addAll(new Text("Start Date : "),startDatePickerEvent);
	    vbox.getChildren().addAll(new Text("End Date : "),endDatePickerEvent);
	    vbox.getChildren().addAll( new Text("Description:"),DescField);
	    //vbox.getChildren().addAll(new Text("Add Image:"),addImage);
	    vbox.getChildren().add(submit);

	    
	    stage.show();

	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	boolean duration = isDuration(startDatePickerEvent,endDatePickerEvent);
            	Event event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,endDatePickerEvent,duration);
            	addEvent(event);
            	//event.display(lineChart, startDate, endDate);

            	stage.close();
            }
        });
	}
	
	public void addEvent(Event event)
=======
	
		
	public void addTimeline(Event e)
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
	{
    	listEvent.add(event);

    	String StartDate = event.getStartDatePickerEvent().getValue().toString();
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	String startYear = splitStartDate[0];
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	String monthStart = chooseMonth(startMonth); 
    	String axisXstart = monthStart + startYear ;
    
    	// Faire deux classes d'event et ajouter la serie comme variable
		

		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().getValue().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

	    	String endYear = splitEndDate[0];
			String monthEnd = chooseMonth(endMonth); 
	    	String axisXend = monthEnd + endYear ;

	        event.getSeries().setName(event.getTitleEvent());
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay));
		}
		else
		{
			 event.getSeries().setName(event.getTitleEvent());
			 event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
		     Tooltip tool = new Tooltip("Hello fefjfeiferferjfreijgiofrejrioejgroj");
		     Tooltip.install( event.getSeries().getNode(), tool);
     		
		}
		
        lineChart.getData().add( event.getSeries());	
        
       
        
        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) {
	    	 ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	 {
				@Override
				public void handle(MouseEvent e) 
				{
					
					Stage stage = new Stage();
			        stage.setTitle("Information event");
				    VBox vbox = new VBox(20);
				    Scene scene = new Scene(vbox, 400, 400);
				    stage.setScene(scene);
				    Label text = null ;
					//text.setStyle("-fx-background-color: coral; -fx-padding: 10px;");
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

			        Button close = new Button("Close");
			        Button delete = new Button("Delete");
			        Button modify = new Button("Modify");

				    vbox.getChildren().add(text);
				  
				    vbox.getChildren().addAll(close,delete,modify);

				    
				    stage.show();
				    
				    close.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent e) {
							stage.close();
							
						}
				    	
				    });
				    
				    delete.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent e) {
					        deleteEvent(event);
					        stage.close();
						}
				    	
				    });
				    
				    modify.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent e) {
							
							stage.close();
							modifyEvent(event);

						}
						});
					
				}
	    	 });
        }

	    
		
	}
	
	public String chooseMonth(int m)
	{
		String month = "" ;
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
	
	public boolean isDuration(DatePicker sd, DatePicker ed)
	{
		if (sd == ed) 
			return false;
		else
			return true ;
	}
	
	public void deleteEvent(Event e)
	{
        lineChart.getData().remove(e.getSeries());

		for(Event event : listEvent)
		{
			if(event==e)
			{
				listEvent.remove(event);	
				break ;
			}
		}
	}
	
	public void saveEvent()
	{
		// FILE READER 
	}

	public void setDatePicker(DatePicker startDateEvent, DatePicker endDateEvent)
	{
		startDateEvent.setValue(startDate.getValue());
		// To discuss
		endDateEvent.setValue(startDate.getValue());
		
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
		{      
	        public DateCell call(final DatePicker datePicker) 
	        {
	            return new DateCell() 
	            {
	                public void updateItem(LocalDate item, boolean empty) 
	                {
	                    super.updateItem(item, empty);
	                    if (item.isBefore(startDateEvent.getValue().plusDays(0))) 
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(item.isAfter(endDate.getValue()))
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    long days = ChronoUnit.DAYS.between(startDateEvent.getValue(), item);
	
	                    setTooltip(new Tooltip("You're choose " + days + " days"));
	                }
	            };
            }
        };
        endDateEvent.setDayCellFactory(dayCellFactory);
        startDateEvent.setDayCellFactory(dayCellFactory);
	}

	public void modifyEvent(Event e)
	{
		for(Event event : listEvent)
		{
			if(event == e)
			{
				listEvent.remove(event);	
				addEventModified(event);
				break ;
			}
		}
		
		
	}
	public void addEventModified(Event e)
	{
		
		Stage stage = new Stage();
        stage.setTitle("Form add event");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

        TextField nameEvent = new TextField();
        nameEvent.setText(e.getTitleEvent());
	    DatePicker startDatePickerEvent = new DatePicker();
	    startDatePickerEvent.setValue(e.getStartDatePickerEvent().getValue());
	    DatePicker endDatePickerEvent = new DatePicker();
	    endDatePickerEvent.setValue(e.getEndDatePickerEvent().getValue());
	    TextField DescField = new TextField();
	    DescField.setText(e.getDescEvent());
	   // Image image= new Image();
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        setDatePicker(startDatePickerEvent,endDatePickerEvent);

	    vbox.getChildren().addAll(new Text("Event name : "),nameEvent);
	    vbox.getChildren().addAll(new Text("Start Date : "),startDatePickerEvent);
	    vbox.getChildren().addAll(new Text("End Date : "),endDatePickerEvent);
	    vbox.getChildren().addAll( new Text("Description:"),DescField);
	    //vbox.getChildren().addAll(new Text("Add Image:"),addImage);
	    vbox.getChildren().add(submit);

	    
	    stage.show();

	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e1) 
            {
            	deleteEvent(e);
            	boolean duration = isDuration(startDatePickerEvent,endDatePickerEvent);
            	Event event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,endDatePickerEvent,duration);
            	addEvent(event);
            	
            	stage.close();
            }
        });
	}
}
