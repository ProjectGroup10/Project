package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @role This class represent the Timeline itself
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class Timeline 
{

	private int id = 0 ; 
	private String title ; 
	private DatePicker startDate ; 
	private DatePicker endDate ;
	// List of events
	private ArrayList<Event> listEvent ;
	// LineChart who represents the "graphic timeline"
    private LineChart<String,Number> lineChart ;


	public Timeline (String title, DatePicker startTime, DatePicker endTime)
	{
		//autoincrement id when creating a timeline
		this.id = id++ ;
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
	 * @return endDate
	 */
	public int getId() {
		return id;
	}

	/**
	 * setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter 
	 * @return endDate
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
	 * Getter 
	 * @return endDate
	 */
	public ArrayList<Event> getListEvent() {
		return listEvent;
	}

	/**
	 * setter
	 * @param listEvent
	 */
	public void setListEvent(ArrayList<Event> listEvent) {
		this.listEvent = listEvent;
	}

	/**
	 * @role this method intialize the lineCart depending on the startDate and the endDate
	 */
	private void initLineChart() 
	{
		String startDateTimeline = startDate.getValue().toString();
    	String[] splitStartDateTimeline = startDateTimeline.split("-");
    	int startYear = Integer.parseInt(splitStartDateTimeline[0]);
    	String endDateTimeline = endDate.getValue().toString();
    	String[] splitEndDateTimeline = endDateTimeline.split("-");
    	int endYear = Integer.parseInt(splitEndDateTimeline[0]);
    	
    	// Used to define the size of the line chart
    	int diffyear = endYear-startYear ;
    	
    	// representing the month
    	ObservableList<String> months = FXCollections.observableArrayList() ;
    	int date ;
    	for (int i = 0; i <= diffyear ; i++)
    	{
    		date = Integer.parseInt(splitStartDateTimeline[0])+i ; 
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
	 * This method is called when cliking on addEvent, a new window comes and ask to fill the 
	 * informations corresponding to create an event
	 */
	public void appearFormEvent()
	{
		Stage stage = new Stage();
        stage.setTitle("Add Event");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

        TextField nameEvent = new TextField();
	    DatePicker startDatePickerEvent = new DatePicker();
	    CheckBox duration = new CheckBox();
	    duration.setText("event with duration ?");
	    DatePicker endDatePickerEvent = new DatePicker();
	    TextField DescField = new TextField();
	   // Image image= new Image();
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        // method to handle the fact that the end date should be after the start date
        setDatePicker(startDatePickerEvent,endDatePickerEvent);

	    vbox.getChildren().addAll(new Text("Event name : "),nameEvent);
	    vbox.getChildren().addAll(new Text("Start Date : "),startDatePickerEvent);
	    vbox.getChildren().add(duration);
	    ArrayList<Node> l = new ArrayList<Node>();
	    l.add(new Text("End Date : "));
	    l.add(endDatePickerEvent);
	    duration.setOnAction(e ->vbox.getChildren().addAll(5, l));
	    
	    
	    vbox.getChildren().addAll( new Text("Description:"),DescField);
	    //vbox.getChildren().addAll(new Text("Add Image:"),addImage);
	    vbox.getChildren().add(submit);

	    
	    stage.show();
	    
		    


	    // listener when cliking on addImage
	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    // Listener when cliking on submit
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	// call the method isDuration to determine if the event is an duration event 
            	// or an non duratio event
        	    Event event ;
				if(duration.isSelected())
                   	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,endDatePickerEvent,true);
        	    else
                   	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,false);

            	addEvent(event);

            	stage.close();
            }
        });
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
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	String startYear = splitStartDate[0];
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	// call the method chooseMonth depending on which month is selected
    	String monthStart = chooseMonth(startMonth); 
    	String axisXstart = monthStart + startYear ;
    		
    	// if it's a duration then add an endDate
		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().getValue().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

	    	String endYear = splitEndDate[0];
			String monthEnd = chooseMonth(endMonth); 
	    	String axisXend = monthEnd + endYear ;

	    	// add the event to the lineChart, using a series from the class event
	        event.getSeries().setName(event.getTitleEvent());
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay));
		    // tooltip permit to display something when just passing the mouse over the event
	        Tooltip tool = new Tooltip("Add something there");
		    Tooltip.install( event.getSeries().getNode(), tool);
		}
		else // it's a non duration event
		{
			 event.getSeries().setName(event.getTitleEvent());
			 event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
			 // tooltip permit to display something when just passing the mouse over the event
			 Tooltip tool = new Tooltip("Add something there");
		     Tooltip.install( event.getSeries().getNode(), tool);
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
        			// display a new window with the information of the event 
					Stage stage = new Stage();
			        stage.setTitle("Information event");
				    VBox vbox = new VBox(20);
				    Scene scene = new Scene(vbox, 400, 400);
				    stage.setScene(scene);
				    Label text = null ;
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

				    // Create three different buttons
			        Button close = new Button("Close");
			        Button delete = new Button("Delete");
			        Button modify = new Button("Modify");

				    vbox.getChildren().add(text);
				  
				    vbox.getChildren().addAll(close,delete,modify);

				    stage.show();
				    
				    // Listener for the close button
				    close.setOnAction(new EventHandler<ActionEvent>()
				    {
						public void handle(ActionEvent e) 
						{
							stage.close();
						}
				    });
				    
				    // Listener for the delete button
				    delete.setOnAction(new EventHandler<ActionEvent>()
				    {
						public void handle(ActionEvent e) 
						{
					        deleteEvent(event);
					        stage.close();
						}
				    });
				    
				    // Listener for the modify button
				    modify.setOnAction(new EventHandler<ActionEvent>()
				    {
						public void handle(ActionEvent e) 
						{
							modifyEvent(event);
							stage.close();
						}
					});
				}
	    	 });
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
	
	/**
	 * @role method to determine if the event is a non duration event or a duration event
	 * @param sd
	 * @param ed
	 * @return true, or false
	 */
	public boolean isDuration(DatePicker sd, DatePicker ed)
	{
		if (sd.getValue() == ed.getValue()) 
			return false;
		else
			return true ;
	}
	
	/**
	 * @role method to handle the fact of deleting an event 
	 * @param e
	 */
	public void deleteEvent(Event e)
	{
		// remove the event (series) from the timeline (lineChart)
        lineChart.getData().remove(e.getSeries());
		// remove the event from the list event 
		listEvent.remove(e);	
	}
	
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
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
	
	/**
	 * @role method who permit to modify an event when clicking on the button modify event
	 * @param e
	 */
	public void modifyEvent(Event e)
	{
		listEvent.remove(e);
		addEventModified(e);
	}
	
	/**
	 * @role Method similar to addEvent except the fact that we set up the previous information
	 * of the modified event
	 * @param e
	 */
	public void addEventModified(Event e)
	{
		
		Stage stage = new Stage();
        stage.setTitle("Form add event");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

	    CheckBox duration = new CheckBox();
	    duration.setText("event with duration ?");

        TextField nameEvent = new TextField();
        nameEvent.setText(e.getTitleEvent());
	    DatePicker startDatePickerEvent = new DatePicker();
	    startDatePickerEvent.setValue(e.getStartDatePickerEvent().getValue());
	    DatePicker endDatePickerEvent = new DatePicker();
	    ArrayList<Node> l = new ArrayList<Node>();
	    l.add(new Text("End Date : "));
	    l.add(endDatePickerEvent);


	    if(e.isDuration())
	    {
		    endDatePickerEvent.setValue(e.getEndDatePickerEvent().getValue());
	    }
	    TextField DescField = new TextField();
	    DescField.setText(e.getDescEvent());
	   // Image image= new Image();
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        setDatePicker(startDatePickerEvent,endDatePickerEvent);

	    vbox.getChildren().addAll(new Text("Event name : "),nameEvent);
	    vbox.getChildren().addAll(new Text("Start Date : "),startDatePickerEvent);
	    vbox.getChildren().addAll(duration);

	    duration.setOnAction(ex ->vbox.getChildren().addAll(5, l));

	    vbox.getChildren().addAll( new Text("Description:"),DescField);
	    //vbox.getChildren().addAll(new Text("Add Image:"),addImage);
	    vbox.getChildren().add(submit);
	    
	    stage.show();

	    // listener when cliking on addImage
	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    

	    // listener when cliking on submit
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e1) 
            {
            	// delete the previous event 
            	deleteEvent(e);
				Event event;
				if(duration.isSelected())
                  	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,endDatePickerEvent,true);
				else
                  	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent,false);
				addEvent(event);
            	
            	stage.close();
            }
        });
	}
}
