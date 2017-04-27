package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * @role This class Display timeline
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class DisplayTimeLine {
	private Timeline timeline;
	private MenuItem DeleteTimeLine;
	private HBox h;
	private Image im;
	private MenuButton menuButton;
	private ArrayList<Event> listEvent = new ArrayList<Event>() ;
	private AddEventFrameWork eventF;
	// LineChart who represents the "graphic timeline"
    private LineChart<String,Number> linechart ;
    
    public DisplayTimeLine (Timeline timeline)
    {
    	this.timeline = timeline;
    }
    /**
	 * This method is called when cliking on addEvent, a new window comes and ask to fill the 
	 * informations corresponding to create an event
	 */
	public void display(VBox v) 
	{
		eventF = new AddEventFrameWork();
		// initialize the series when creating an event
    	initLineChart();
		
        DeleteTimeLine = new MenuItem("Delete Timeline");
        MenuItem AddEvent = new MenuItem("Add Event");
        menuButton = new MenuButton("Options", null, DeleteTimeLine, AddEvent );
       
        // Listener when clicking on add event
        AddEvent.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				eventF.addtimeline_Page();
				setDatePicker(eventF.getStartDateper(),eventF.getEndDateper());
				eventF.getbuttonType().setOnAction(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent e) {
						if(eventF.getTextfield().getText() != null && !eventF.getTextfield().getText().isEmpty() )
						{
							if(eventF.getEvent().getStartTime().getYear() > eventF.getEvent().getEndTime().getYear())
							{
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("Please check the date of you pick!");
								alert.showAndWait();
							}
							else if(eventF.getEvent().getStartTime().getYear() == eventF.getEvent().getEndTime().getYear())
							{
								if(eventF.getEvent().getStartTime().getMonthValue() > eventF.getEvent().getEndTime().getMonthValue() )
								{
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("Please check the date of you pick!");
									alert.showAndWait();
								}
								else if(eventF.getEvent().getStartTime().getMonthValue() == eventF.getEvent().getEndTime().getMonthValue())
								{
									if(eventF.getEvent().getStartTime().getDayOfMonth() > eventF.getEvent().getEndTime().getDayOfMonth())
									{
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("Please check the date of you pick!");
										alert.showAndWait();
									}
									else
									{
										addEvent(eventF.getEvent());
										eventF.getStage().close();
									}
								}
								else
								{
									addEvent(eventF.getEvent());
									eventF.getStage().close();
								}
							}
							else
							{
								addEvent(eventF.getEvent());
								eventF.getStage().close();
							}
						}
						else
						{
							eventF.getLabel().setText("Title cannot be null!");
						}
					}
				  });						 
			}});
        
        h = new HBox();
        StackPane root = new StackPane();
        root.getChildren().add(linechart);
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(50, 50);
        ap.getChildren().add(menuButton);
        h.getChildren().addAll(ap,root);
	    
        v.getChildren().add(h);       
	}
	/**
	 * @role method who "transform" the integer passed in parameters into the corresponding 
	 * month
	 * @param m
	 * @return month (string)
	 */
	private String chooseMonth(int m)
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
	/**
	 * @role this method intialize the lineCart depending on the startDate and the endDate
	 */
	private void initLineChart() 
	{
		String StartDate = timeline.getStartTime().getValue().toString();
    	String[] splitStartDate = StartDate.split("-");
    	int startYear = Integer.parseInt(splitStartDate[0]);
    	String EndDate = timeline.getEndTime().getValue().toString();
    	String[] splitEndDate = EndDate.split("-");
    	int endYear = Integer.parseInt(splitEndDate[0]);
    	// Used to define the size of the line chart
    	int diffyear = endYear-startYear ;
    	// representing the mont
    	ObservableList<String> months = FXCollections.observableArrayList() ;
    	int date ;
    	for (int i = 0; i <= diffyear ; i++)
    	{
    		date = Integer.parseInt(splitStartDate[0])+i ; 
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
        
        linechart = new LineChart<String, Number>(xAxis, yAxis);
        linechart.setTitle(timeline.getTitle() + " (From " + timeline.getStartTime().getValue().toString() + " to " + timeline.getEndTime().getValue().toString() + ")");
        linechart.setMinHeight(450);
        linechart.setMinWidth((diffyear+1) * 500);
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
    	String StartDate = event.getStartTime().toString();
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
			EndDate = event.getEndTime().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

	    	String endYear = splitEndDate[0];
			String monthEnd = chooseMonth(endMonth); 
	    	String axisXend = monthEnd + endYear ;
	    	// add the event to the lineChart, using a series from the class event
	        event.getSeries().setName(event.getTitle());
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay));
		    // tooltip permit to display something when just passing the mouse over the event
	        Tooltip tool = new Tooltip("Add something there");
		    Tooltip.install( event.getSeries().getNode(), tool);
		}
		else// it's a non duration event
		{
			 event.getSeries().setName(event.getTitle());
			 event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay));
			 // tooltip permit to display something when just passing the mouse over the event
			 Tooltip tool = new Tooltip("Add something there");
		     Tooltip.install( event.getSeries().getNode(), tool);
		}
		// add the series to the lineChart
		linechart.getData().add( event.getSeries());	
		
        // for each data for a series
        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) {
        	// when clicking on the event 
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	 {
				@Override
				public void handle(MouseEvent e) 
				{
					ImageView myImageView = new ImageView(event.getImage());
	                myImageView.setFitHeight(50);
	                myImageView.setFitWidth(50);
	                
        			// display a new window with the information of the event 
					Stage stage = new Stage();
			        stage.setTitle("Information event");
				    VBox vbox = new VBox(20);
				    Scene scene = new Scene(vbox, 400, 300);
				    stage.setScene(scene);
				    Label text = null;
					//text.setStyle("-fx-background-color: coral; -fx-padding: 10px;");
					if(event.isDuration())
					{
						text = new Label(("Title Event :  " + event.getTitle() + "\n" +
								"Start date event : " + StartDate + "\n" + 
								"End date event : " + event.getEndTime().toString() + "\n" + 
								"Description : " + event.getDescription()),myImageView );
					}
					else
					{
						text = new Label(("Title Event :  " + event.getTitle() + "\n" +
								"Start date event : " + StartDate + "\n" + 
								"Description : " + event.getDescription()),myImageView );
					}

					HBox h = new HBox();
				    // Create three different buttons
			        Button close = new Button("Close");
			        Button delete = new Button("Delete");
			        Button modify = new Button("Modify");
				    vbox.getChildren().add(text);
				    h.getChildren().addAll(close,delete,modify);
					h.setPadding(new Insets(10,10,10,10));
					h.setSpacing(10.0);
				    vbox.getChildren().addAll(h);
				    
				    stage.show();
				    
				    // Listener for the close button
				    close.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent e) {
							stage.close();
						}
				    	
				    });
				    
				    // Listener for the delete button
				    delete.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent e) {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation Dialog");
							alert.setContentText("Confirm to delete event in timeline");
							Optional<ButtonType> confirmation = alert.showAndWait();
							if(confirmation.isPresent() && confirmation.get() == ButtonType.OK)
							{
								deleteEvent(event);
						        stage.close();
							}
							
						}
				    	
				    });
				    
				    // Listener for the modify button
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
	/**
	 * @role Method similar to addEvent except the fact that we set up the previous information
	 * of the modified event
	 * @param e
	 */
	private void addEventModified(Event e)
	{
		
		Stage stage = new Stage();
        stage.setTitle("Form add event");
	    GridPane GP = new GridPane();
	    Scene scene = new Scene(GP, 330, 330);
	    stage.setScene(scene);
	    
	    GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		
        TextField nameEvent = new TextField();
        nameEvent.setText(e.getTitle());
	    DatePicker startDatePickerEvent = new DatePicker();
	    startDatePickerEvent.setValue(e.getStartTime());
	    DatePicker endDatePickerEvent = new DatePicker();
	    endDatePickerEvent.setValue(e.getEndTime());
	    TextArea DescField = new TextArea();
	    DescField.setText(e.getDescription());
		setDatePicker(startDatePickerEvent,endDatePickerEvent);
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        Label label = new Label();
		label.setFont(new Font("Sans Serif",18));
		label.setTextFill(Color.RED);

		GP.add(new Text("Event name: "), 0, 0 );
	    GP.add(nameEvent, 1, 0 );
	    GP.add(new Text("Start Date: "), 0, 1 );
	    GP.add(startDatePickerEvent, 1, 1 );
	    GP.add(new Text("End Date: "), 0, 2 );
	    GP.add(endDatePickerEvent, 1, 2 );
	    GP.add(new Text("Description: "), 0, 3 );
	    GP.add(DescField, 1, 3 );
	    GP.add(new Text("Add Image: "), 0, 4 );
	    GP.add(addImage, 1, 4 );
		GP.add(label, 1, 5);
	    GP.add(submit, 1, 6 );

	    stage.show();
	    
	    // listener when cliking on addImage
	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent eve) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
	             
				 FileChooser.ExtensionFilter extFilterJPG = 
		                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
		            FileChooser.ExtensionFilter extFilterjpg = 
		                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
		            FileChooser.ExtensionFilter extFilterPNG = 
		                    new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
		            FileChooser.ExtensionFilter extFilterpng = 
		                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		            fileChooser.getExtensionFilters()
		                    .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
	            File file = fileChooser.showOpenDialog(null);
	                       
	            try {
	                BufferedImage bufferedImage = ImageIO.read(file);
	                im = SwingFXUtils.toFXImage(bufferedImage, null);
	            } catch (IOException e) {
	            	e.getStackTrace();
	            }
			}
	    	
	    });
	    
	    // listener when cliking on submit
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e1) 
            {
            	if(nameEvent.getText() != null && ! nameEvent.getText().isEmpty())
            	{
            		if(startDatePickerEvent.getValue().getYear() > endDatePickerEvent.getValue().getYear())
    				{
    					Alert alert = new Alert(AlertType.WARNING);
    					alert.setTitle("Warning");
    					alert.setHeaderText("Please check the date of you pick!");
    					alert.showAndWait();
    				}
    				else if(startDatePickerEvent.getValue().getYear() == endDatePickerEvent.getValue().getYear())
    				{
    					if(startDatePickerEvent.getValue().getMonthValue() > endDatePickerEvent.getValue().getMonthValue() )
    					{
    						Alert alert = new Alert(AlertType.WARNING);
    						alert.setTitle("Warning");
    						alert.setHeaderText("Please check the date of you pick!");
    						alert.showAndWait();
    					}
    					else if(startDatePickerEvent.getValue().getMonthValue() == endDatePickerEvent.getValue().getMonthValue())
    					{
    						if(startDatePickerEvent.getValue().getDayOfMonth() > endDatePickerEvent.getValue().getDayOfMonth())
    						{
    							Alert alert = new Alert(AlertType.WARNING);
    							alert.setTitle("Warning");
    							alert.setHeaderText("Please check the date of you pick!");
    							alert.showAndWait();
    						}
    						else
    						{
    			            	// delete the previous event 
    							deleteEvent(e);
    			            	boolean duration = IsDuration(startDatePickerEvent.getValue(),endDatePickerEvent.getValue());
    			            	// create the new Event
    			            	Event event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),duration,endDatePickerEvent.getValue(),im);
    			            	addEvent(event);
    			            	stage.close();
    						}
    					}
    					else
    					{
    						// delete the previous event
    						deleteEvent(e);
    		            	boolean duration = IsDuration(startDatePickerEvent.getValue(),endDatePickerEvent.getValue());
    		            	// create the new Event
    		            	Event event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),duration,endDatePickerEvent.getValue(),im);
    		            	addEvent(event);
    		            	stage.close();
    					}
    				}
    				else
    				{
    					// delete the previous event
    					deleteEvent(e);
    	            	boolean duration = IsDuration(startDatePickerEvent.getValue(),endDatePickerEvent.getValue());
    	            	// create the new Event
    	            	Event event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),duration,endDatePickerEvent.getValue(),im);
    	            	addEvent(event);
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
	/**
	 * @role method to determine if the event is a non duration event or a duration event
	 * @param sd
	 * @param ed
	 * @return true, or false
	 */
	private boolean IsDuration(LocalDate start,LocalDate end)
	{
		if(start.equals(end))
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	/**
	 * @role method who permit to modify an event when clicking on the button modify event
	 * @param e
	 */
	private void modifyEvent(Event e)
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
	/**
	 * @role method to handle the fact of deleting an event 
	 * @param e
	 */
	private void deleteEvent(Event e)
	{
		
		linechart.getData().remove(e.getSeries());
		for(Event event : listEvent)
		{
			if(event==e)
			{
				listEvent.remove(event);	
				break ;
			}
		}
	}
	
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
	private void setDatePicker(DatePicker startDateEvent, DatePicker endDateEvent)
	{
		startDateEvent.setValue(timeline.getStartTime().getValue());
		// To discuss
		endDateEvent.setValue(timeline.getStartTime().getValue());
		
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
	                    if(item.isAfter(timeline.getEndTime().getValue()))
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
        
        final Callback<DatePicker, DateCell> dayCell = new Callback<DatePicker, DateCell>() 
		{      
	        public DateCell call(final DatePicker datePicker) 
	        {
	            return new DateCell() 
	            {
	                public void updateItem(LocalDate item, boolean empty) 
	                {
	                    super.updateItem(item, empty);
	                    if (item.isBefore(timeline.getStartTime().getValue().plusDays(0))) 
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(item.isAfter(timeline.getEndTime().getValue()))
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
        startDateEvent.setDayCellFactory(dayCell);
	}
	/**
	 * Getter
	 * @return timeline
	 */
	public Timeline getTime()
	{
		return timeline;
	}
	/**
	 * Getter
	 * @return menuButton
	 */
	public MenuButton getMenuButoon()
	{
		return menuButton;
	}	
	/**
	 * Getter
	 * @return h
	 */
	public HBox getHbox()
	{
		return h;
	}
	/**
	 * Getter
	 * @return DeleteTimeLine
	 */
	public  MenuItem getDeleteitem()
	{
		return DeleteTimeLine;
	}
	/**
	 * Getter
	 * @return listEvent
	 */
	public ArrayList<Event> getListEvent() {
		return listEvent;
	}
}
