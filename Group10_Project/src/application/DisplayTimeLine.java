package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class DisplayTimeLine {
	private Timeline timeline;
	private AddEventFrameWork eventF;
    private LineChart<String,Number> scatterChart ;
    
    public DisplayTimeLine (Timeline timeline)
    {
    	this.timeline = timeline;
    }
	public void display(VBox v) 
	{
		eventF = new AddEventFrameWork();
		
		String StartDate = timeline.getStartTime().getValue().toString();
    	String[] splitStartDate = StartDate.split("-");
    	int startYear = Integer.parseInt(splitStartDate[0]);
    	String EndDate = timeline.getEndTime().getValue().toString();
    	String[] splitEndDate = EndDate.split("-");
    	int endYear = Integer.parseInt(splitEndDate[0]);
    	
    	HBox h = new HBox();
    	
    	int diffyear = endYear-startYear ;
        	
    	
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Months (Years)");
        
        xAxis.setTickLabelRotation(90);
        
        
        NumberAxis yAxis = new NumberAxis(0,31,5);
        yAxis.setLabel("Days");
        
        scatterChart = new LineChart<String, Number>(xAxis, yAxis);
        scatterChart.setTitle(timeline.getTitle());
        scatterChart.setMinHeight(450);
        scatterChart.setMinWidth((diffyear+1) * 500);
        
        MenuItem DeleteTimeLine = new MenuItem("Delete Timeline");
        MenuItem AddEvent = new MenuItem("Add Event");
        MenuItem ModifyEvent = new MenuItem("Modify Event");
        MenuItem DeleteEvent = new MenuItem("Delete Event");


        MenuButton menuButton = new MenuButton("Options", null, DeleteTimeLine, AddEvent , ModifyEvent, DeleteEvent);

        AddEvent.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				eventF.addtimeline_Page();
				setDatePicker();
				eventF.getDialog().setResultConverter(new Callback<ButtonType, Event>(){

					@Override
					public Event call(ButtonType param) {
						// TODO Auto-generated method stub
						if(param == eventF.getbuttonType())
						{
							if(eventF.getEvent().getEndTime().getYear() == eventF.getEvent().getStartTime().getYear())
							{
								if(eventF.getEvent().getEndTime().getMonthValue() == eventF.getEvent().getStartTime().getMonthValue())
								{
									if(eventF.getEvent().getEndTime().getDayOfMonth() < eventF.getEvent().getStartTime().getDayOfMonth())
									{
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("Please check the date of you pick!");
										alert.showAndWait();
									}
									else
									{
										String s  = eventF.getEvent().getStartTime().getMonth().toString();
										String save = " ";
										for(int i = 0; i < 3; i++)
										{
											if(i == 0)
											{
												save = save + s.charAt(i);
											}
											else
											{
												save = save + s.toLowerCase().charAt(i);
											}
										}
										save = save + " " + eventF.getEvent().getStartTime().getYear();
										String s2  = eventF.getEvent().getEndTime().getMonth().toString();
										String save2 = " ";
									
										for(int i = 0; i < 3; i++)
										{
											if(i == 0)
											{
												save2 = save2 + s2.charAt(i);
											}
											else
											{
												save2 = save2 + s2.toLowerCase().charAt(i);
											}
										}
										save2 = save2 + " " + eventF.getEvent().getStartTime().getYear();
								        if (scatterChart.getData() == null) {
								        	scatterChart.setData(FXCollections.<XYChart.Series<String,Number>>observableArrayList());
							            }

								        XYChart.Series<String,Number> series
							                    = new XYChart.Series<>();
							            series.setName("Event: " + eventF.getEvent().getTitle());
							            series.getData().add(new XYChart.Data<>(
							                		save,  eventF.getEvent().getStartTime().getDayOfMonth()));
							            series.getData().add(new XYChart.Data<>(
						                		save2,  eventF.getEvent().getEndTime().getDayOfMonth()));
							            scatterChart.getData().add(series); 
							            for (XYChart.Series<String, Number> ss : scatterChart.getData()) {
							                for (XYChart.Data<String, Number> d : ss.getData()) {
							                	ImageView myImageView = new ImageView();
							                	myImageView.setImage(eventF.getEvent().getImage());
							                    myImageView.setFitHeight(50);
							                    myImageView.setFitWidth(50);
							                    
							                	if(eventF.IsDuration())
							                	{
								                    Tooltip tooltip = new Tooltip("TitleLine: " + eventF.getEvent().getTitle() + "\n"
								                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
								                 			+ "StartDate: " + eventF.getEvent().getStartTime() + "\n"
								                 			+ "EndDate: " + eventF.getEvent().getEndTime());
								                    tooltip.setGraphic(myImageView);
							                		Tooltip.install(d.getNode(), tooltip
								                            );
							                		
							                	}
							                	
							                	else
							                	{
							                		Tooltip tooltip = new Tooltip( "TitleLine: " + eventF.getEvent().getTitle() + "\n"
								                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
								                 			+ "Time: " + eventF.getEvent().getStartTime() + "\n");
								                    tooltip.setGraphic(myImageView);
							                		Tooltip.install(d.getNode(), tooltip);
							                	}
							                    
							                }
							            }
										
									}
								}
								else if(eventF.getEvent().getEndTime().getMonthValue() < eventF.getEvent().getStartTime().getMonthValue())
								{
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("Please check the date of you pick!");
									alert.showAndWait();
								}
								else
								{
									String s  = eventF.getEvent().getStartTime().getMonth().toString();
									String save = " ";
									for(int i = 0; i < 3; i++)
									{
										if(i == 0)
										{
											save = save + s.charAt(i);
										}
										else
										{
											save = save + s.toLowerCase().charAt(i);
										}
									}
									save = save + " " + eventF.getEvent().getStartTime().getYear();
									String s2  = eventF.getEvent().getEndTime().getMonth().toString();
									String save2 = " ";
								
									for(int i = 0; i < 3; i++)
									{
										if(i == 0)
										{
											save2 = save2 + s2.charAt(i);
										}
										else
										{
											save2 = save2 + s2.toLowerCase().charAt(i);
										}
									}
									save2 = save2 + " " + eventF.getEvent().getStartTime().getYear();
							        if (scatterChart.getData() == null) {
							        	scatterChart.setData(FXCollections.<XYChart.Series<String,Number>>observableArrayList());
						            }

							        XYChart.Series<String,Number> series
						                    = new XYChart.Series<>();
						            series.setName("Event: " + eventF.getEvent().getTitle());
						            series.getData().add(new XYChart.Data<>(
						                		save,  eventF.getEvent().getStartTime().getDayOfMonth()));
						            series.getData().add(new XYChart.Data<>(
					                		save2,  eventF.getEvent().getEndTime().getDayOfMonth()));
						            scatterChart.getData().add(series); 
						            for (XYChart.Series<String, Number> ss : scatterChart.getData()) {
						                for (XYChart.Data<String, Number> d : ss.getData()) {
						                	ImageView myImageView = new ImageView();
						                	myImageView.setImage(eventF.getEvent().getImage());
						                    myImageView.setFitHeight(50);
						                    myImageView.setFitWidth(50);
						                    
						                	if(eventF.IsDuration())
						                	{
							                    Tooltip tooltip = new Tooltip("TitleLine: " + eventF.getEvent().getTitle() + "\n"
							                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
							                 			+ "StartDate: " + eventF.getEvent().getStartTime() + "\n"
							                 			+ "EndDate: " + eventF.getEvent().getEndTime());
							                    tooltip.setGraphic(myImageView);
						                		Tooltip.install(d.getNode(), tooltip
							                            );
						                		
						                	}
						                	
						                	else
						                	{
						                		Tooltip tooltip = new Tooltip( "TitleLine: " + eventF.getEvent().getTitle() + "\n"
							                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
							                 			+ "Time: " + eventF.getEvent().getStartTime() + "\n");
							                    tooltip.setGraphic(myImageView);
						                		Tooltip.install(d.getNode(), tooltip);
						                	}
						                    
						                }
						            }
									
								}
							}
							else if(eventF.getEvent().getEndTime().getYear() < eventF.getEvent().getStartTime().getYear())
							{
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("Please check the date of you pick!");
								alert.showAndWait();
							}
							else
							{
								String s  = eventF.getEvent().getStartTime().getMonth().toString();
								String save = " ";
								for(int i = 0; i < 3; i++)
								{
									if(i == 0)
									{
										save = save + s.charAt(i);
									}
									else
									{
										save = save + s.toLowerCase().charAt(i);
									}
								}
								save = save + " " + eventF.getEvent().getStartTime().getYear();
								String s2  = eventF.getEvent().getEndTime().getMonth().toString();
								String save2 = " ";
							
								for(int i = 0; i < 3; i++)
								{
									if(i == 0)
									{
										save2 = save2 + s2.charAt(i);
									}
									else
									{
										save2 = save2 + s2.toLowerCase().charAt(i);
									}
								}
								save2 = save2 + " " + eventF.getEvent().getStartTime().getYear();
						        if (scatterChart.getData() == null) {
						        	scatterChart.setData(FXCollections.<XYChart.Series<String,Number>>observableArrayList());
					            }

						        XYChart.Series<String,Number> series
					                    = new XYChart.Series<>();
					            series.setName("Event: " + eventF.getEvent().getTitle());
					            series.getData().add(new XYChart.Data<>(
					                		save,  eventF.getEvent().getStartTime().getDayOfMonth()));
					            series.getData().add(new XYChart.Data<>(
				                		save2,  eventF.getEvent().getEndTime().getDayOfMonth()));
					            scatterChart.getData().add(series); 
					            for (XYChart.Series<String, Number> ss : scatterChart.getData()) {
					                for (XYChart.Data<String, Number> d : ss.getData()) {
					                	ImageView myImageView = new ImageView();
					                	myImageView.setImage(eventF.getEvent().getImage());
					                    myImageView.setFitHeight(50);
					                    myImageView.setFitWidth(50);
					                    
					                	if(eventF.IsDuration())
					                	{
						                    Tooltip tooltip = new Tooltip("TitleLine: " + eventF.getEvent().getTitle() + "\n"
						                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
						                 			+ "StartDate: " + eventF.getEvent().getStartTime() + "\n"
						                 			+ "EndDate: " + eventF.getEvent().getEndTime());
						                    tooltip.setGraphic(myImageView);
					                		Tooltip.install(d.getNode(), tooltip
						                            );
					                		
					                	}
					                	
					                	else
					                	{
					                		Tooltip tooltip = new Tooltip( "TitleLine: " + eventF.getEvent().getTitle() + "\n"
						                 			+ "Description: " + eventF.getEvent().getDescription() + "\n"
						                 			+ "Time: " + eventF.getEvent().getStartTime() + "\n");
						                    tooltip.setGraphic(myImageView);
					                		Tooltip.install(d.getNode(), tooltip);
					                	}
					                    
					                }
					            }
								
							}
						
						}
						return null;
						}});
				eventF.getDialog().showAndWait();
			}});
        
       // Button closeButton = new Button("Close all timelines");
       
        StackPane root = new StackPane();
        root.getChildren().add(scatterChart);
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(50, 50);
        ap.getChildren().add(menuButton);
        h.getChildren().addAll(ap,root);
	    
        v.getChildren().add(h);
       
        /*
        closeButton.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	v.getChildren().remove();
            }
	    });*/
        
        
		
		/*System.out.println("Title of timeline : " + title + "\n"+ 
							"Start time " + startTime.toString() + "\n" + 
							"End Time " + endTime.toString());	
		for (Event event : listEvent) 
		{
		    System.out.println("Title of event " + event.getTitle() + "\n" +
		    				   " Description " + event.getDescription() + "\n" +
		    					" Start Time " + event.getStartTime().getTime() + "\n");
		    if(event.isDuration())
		    {
		    	System.out.println("End Time" + event.getEndTime().getTime());
		    }
		}*/
	}
	public void setDatePicker()
	{
		eventF.getStartDateper().setValue(timeline.getStartTime().getValue());
		eventF.getEndDateper().setValue(timeline.getStartTime().getValue());
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                            if (item.isBefore(
	                            		eventF.getStartDateper().getValue().plusDays(0))
	                                ) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }
	                            if(item.isAfter(timeline.getEndTime().getValue()))
	                            {
	                            	setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }
	                            long days = ChronoUnit.DAYS.between(
	                            		eventF.getStartDateper().getValue(), item
	                            );

	                            setTooltip(new Tooltip(
	                                "You're choose " + days + " days")
	                            );
	                            }
	                };
	            }
	        };
	        eventF.getEndDateper().setDayCellFactory(dayCellFactory);
	        eventF.getStartDateper().setDayCellFactory(dayCellFactory);
	}	
}
