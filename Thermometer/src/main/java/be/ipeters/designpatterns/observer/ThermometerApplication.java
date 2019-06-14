package be.ipeters.designpatterns.observer;

import java.beans.EventHandler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ThermometerApplication extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	private final TemperatureSensor sensor = new TemperatureSensor();
	private final Label temperatureLabel = new Label(String.valueOf(sensor.getCurrentReading()));

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		primaryStage.setTitle("Thermometer");
		HBox root = new HBox();
		root.getChildren().add(new Label("Current temperature: "));
		root.getChildren().add(temperatureLabel);
		primaryStage.setScene(new Scene(root, 300, 50));
		primaryStage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				sensor.shutdown();
			}
		});
//		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
////			@Override
////			public void handle(WindowEvent event) {
////				sensor.shutdown();
////			}
//		});
		primaryStage.show();
		
		sensor.addListener(new TemperatureSensorListener() {
			
			@Override
			public void onReadingChange() {
				updateTemperatureLabel();
				
			}
		});
	}
	private void updateTemperatureLabel() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				temperatureLabel.setText(String.valueOf(sensor.getCurrentReading()));
				
			}
		});
		
		
	}

}
