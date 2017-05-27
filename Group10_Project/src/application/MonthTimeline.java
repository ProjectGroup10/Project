package application;

// include the jar to use the controlsfx popOver
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @role This class extends the abstract class Timeline and represent a timeline during months
 * @author Meng Li, Frapper Colin
 * @date 05/23/2017
 * @note : You have to include the controlsfx jar to make it work
 */
public class MonthTimeline extends Timeline
{
    private Image im;

	/**
	 * Constructor for the class MonthTimeline
	 * @param title
	 * @param startTime
	 * @param endTime
	 */
	public MonthTimeline(String title, LocalDate startTime, LocalDate endTime) {
		super(title, startTime, endTime);

		//autoincrement id when creating a timeline
		this.id = id++ ;
		this.listEvent = new ArrayList<Event>() ;
	}

	// 
	/**
	 * @role : Method to initialize the line chart of the corresponding timeline
	 */

	protected void initLineChart() 
	{
		String startDateTimeline = startDate.toString();
    	String[] splitStartDateTimeline = startDateTimeline.split("-");
    	int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
    	String endDateTimeline = endDate.toString();
    	String[] splitEndDateTimeline = endDateTimeline.split("-");
    	int endMonth = Integer.parseInt(splitEndDateTimeline[1]);
    	
    	// representing the month
    	ObservableList<String> months = FXCollections.observableArrayList() ;
    	
		for(int j = startMonth ; j <=endMonth ; j++)
		{
			// using the method chooseMonth
			months.addAll(chooseMonth(j));
		}
    			
    	// xAxis 
        CategoryAxis xAxis = new CategoryAxis(months);
        xAxis.setLabel("Months  " + splitEndDateTimeline[0]);
        xAxis.setTickLabelRotation(90);
        
        // yAxis represente the days
        NumberAxis yAxis = new NumberAxis(0,31,5);
        yAxis.setLabel("Days");
        
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle(title + " (From " + startDateTimeline + " to " + endDateTimeline + ")");
        lineChart.setMinHeight(450);
        lineChart.setMinWidth(500);
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
    	
    	String StartDate = event.getStartDatePickerEvent().toString();
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	// call the method chooseMonth depending on which month is selected
    	String axisXstart = chooseMonth(startMonth); 
    		
    	// if it's a duration then add an endDate
		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

			String axisXend = chooseMonth(endMonth); 

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
        lineChart.getData().add(event.getSeries());	
        
        // for each data for a series

        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
        {
    
        	// tooltip permit to display something when just passing the mouse over the event
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() 
			{
				public void handle(MouseEvent e) 
				{
				    String text = null ;

					for(int i = 0 ; i< listEvent.size() ; i++)
		        	{	
        				Event ev = listEvent.get(i);

						LocalDate a = event.getStartDatePickerEvent();
	        			
						// set the LocalDate to d, otherwise provide a null error when testing
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = ev.getStartDatePickerEvent();
	        			
	        			// set the LocalDate to c, otherwise provide a null error when testing
        				LocalDate c = b ;
						if (ev.isDuration())
	        				c = ev.getEndDatePickerEvent();

						if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
							if(ev.isDuration())
							{

								text = new String("Title Event:  " + ev.getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event : " + ev.getEndDatePickerEvent().toString() + "\n" );
							}
							else
							{

								text = new String("Title Event:  " + ev.getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" );
							}
		        		}
						
						Tooltip tool = new Tooltip(text);
						Tooltip.install( ss.getNode(), tool);

		        	}

				}
        		
			});

        	// when clicking on the event 
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	{
        		public void handle(MouseEvent e) 
				{
        			for(int i = 0 ; i< listEvent.size() ; i++)
		        	{   
        				Event ev = listEvent.get(i);

    				    ImageView image = new ImageView();

    				    if(ev.getImageEvent() != null)
    				    {
    					    im = SwingFXUtils.toFXImage(ev.getImageEvent(), null);
    				    }
    				    else
    				    {
    				    	im = null;
    				    }
    				    image.setImage(im);
    				    image.setFitHeight(40);
    				    image.setFitWidth(40);

    				    PopOver popover = new PopOver();     
        				
	        			LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = ev.getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (ev.isDuration())
	        				c = ev.getEndDatePickerEvent();


				        Button close = new Button("Close");
				        Button delete = new Button("Delete");
				        Button modify = new Button("Modify");
				        close.setPrefSize(90, 20);
				        delete.setPrefSize(90, 20);
				        modify.setPrefSize(90, 20);

		        		if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
						    // Create three different buttons
						    VBox vbox = new VBox();
						    vbox.setPadding(new Insets(10,10,10,10));
						    StackPane s = new StackPane(vbox);
						    
						    Label text = null ;
					    	String description = ev.getDescEvent();

					    	if(description.isEmpty())
					    	{
					    		description = "none" ;
					    	}
						    // if the event is a duration event
						    if(ev.isDuration())
							{
						    	
								text = new Label("Title Event:  " + ev.getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + ev.getEndDatePickerEvent().toString() + "\n" + 
										"Description: " + description );
								text.setTextFill(Color.BLACK);
								
								if(event.getImageEvent() != null)
								{
									vbox.getChildren().add(image);
								}
								vbox.getChildren().add(text);
								HBox h = new HBox();
								h.getChildren().addAll(close,modify,delete);
								vbox.getChildren().add(h);
			        	        vbox.setStyle("-fx-background-color:white;");

			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);
		        		        popover.show(ss.getNode());
							}
							else
							{
								text = new Label("Title Event:  " + ev.getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"Description: " + description );
								
								text.setTextFill(Color.BLACK);
								if(ev.getImageEvent() != null)
								{
									vbox.getChildren().add(image);
								}
								vbox.getChildren().add(text);
								HBox h = new HBox();
								h.getChildren().addAll(close,modify,delete);
								vbox.getChildren().add(h);
			        	        vbox.setStyle("-fx-background-color: white;");

								
			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);

		        				popover.show(ss.getNode());

							}
						    
						    // Listener for the close button
						    close.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									popover.hide();
								}
						    });
						    
						    // Listener for the delete button
						    delete.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									Alert alert = new Alert(AlertType.CONFIRMATION);
					            	alert.setTitle("Confirmation Dialog");
					            	alert.setContentText("Are you sure?");

					            	Optional<ButtonType> result = alert.showAndWait();
					            	if (result.get() == ButtonType.OK)
					            	{
								        deleteEvent(ev);
										popover.hide();
					            	}
								}
						    });
						    
						    // Listener for the modify button
						    modify.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									modifyEvent(ev);
									popover.hide();
								}
							});
		        		}
	
		        	}
				}
	    	});   
        im = null;
        }	
	}
	
	/**
	 * @role method used in the display mode, permit to display the event added, but impossible to modify or delete this event 
	 */
	public void DisplayAddEvent(Event event)
	{    	
    	listEvent.add(event);

    	String StartDate = event.getStartDatePickerEvent().toString();
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	// call the method chooseMonth depending on which month is selected
    	String axisXstart = chooseMonth(startMonth); 
    		
    	// if it's a duration then add an endDate
		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

			String axisXend = chooseMonth(endMonth); 

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
        lineChart.getData().add(event.getSeries());	
        
        // for each data for a series
        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
        {
		    // tooltip permit to display something when just passing the mouse over the event
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() 
			{
				public void handle(MouseEvent e) 
				{
				    String text = null ;

					for(int i = 0 ; i< listEvent.size() ; i++)
		        	{	
						LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();

						if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
							if(listEvent.get(i).isDuration())
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" );
							}
							else
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" );
							}
		        		}		
						Tooltip tool = new Tooltip(text);
						Tooltip.install( ss.getNode(), tool);
		        	}
				}
			});
        	
        	// when clicking on the event 
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	{
        		public void handle(MouseEvent e) 
				{
        			for(int i = 0 ; i< listEvent.size() ; i++)
		        	{
    				    Image im;
    				    if(listEvent.get(i).getImageEvent() != null)
    				    {
    					    im = SwingFXUtils.toFXImage(listEvent.get(i).getImageEvent(), null);
    				    }
    				    else
    				    {
    				    	im = null;
    				    }
    				    ImageView image = new ImageView();
    				    image.setImage(im);
    				    image.setFitHeight(40);
    				    image.setFitWidth(40);

        		        PopOver popover = new PopOver();     
        				
	        			LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();
		        		Button close = new Button("Close");
		        		close.setPrefSize(90, 20);
				       
						if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
						    // Create three different buttons
						    VBox vbox = new VBox();
						    vbox.setPadding(new Insets(10,10,10,10));
						    StackPane s = new StackPane(vbox);
						    
						    Label text = null ;
					    	String description = listEvent.get(i).getDescEvent();

					    	if(description.isEmpty())
					    	{
					    		description = "none" ;
					    	}
						    // if the event is a duration event
						    if(listEvent.get(i).isDuration())
							{
						    	
								text = new Label("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" + 
										"Description: " + description );
								text.setTextFill(Color.BLACK);
								
								
								if(listEvent.get(i).getImageEvent() != null)
								{
									vbox.getChildren().add(image);
								}
								vbox.getChildren().addAll(text,close);
			        	        vbox.setStyle("-fx-background-color: white;");

			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);
		        		        popover.show(ss.getNode());
							}
							else
							{
								text = new Label("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"Description: " + description );
								
								text.setTextFill(Color.BLACK);
								if(listEvent.get(i).getImageEvent() != null)
								{
									vbox.getChildren().add(image);
								}
								vbox.getChildren().addAll(text,close);
			        	        vbox.setStyle("-fx-background-color: white;");

								
			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);

		        				popover.show(ss.getNode());

							}
		        		}
		        		// Listener for the close button
					    close.setOnAction(new EventHandler<ActionEvent>()
					    {
							public void handle(ActionEvent e) 
							{
								popover.hide();
							}
					    });
		        	}
				}
	    	});    
        }	
        
	
	}

}
