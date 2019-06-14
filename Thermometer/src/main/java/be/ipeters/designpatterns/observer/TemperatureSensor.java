package be.ipeters.designpatterns.observer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TemperatureSensor {
	private float currentReading = 67;
	private boolean stopWasRequested = false;
	private final ExecutorService service = Executors.newCachedThreadPool();
	
	public TemperatureSensor() {
		
		service.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!stopWasRequested) {
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {
						// do nothing
					}
					currentReading--; // aftellen
					System.out.println("Current temp is "+currentReading);
				}
			}
			
		});
	}
	public float getCurrentReading() {
		return currentReading;
	}
	
	public void shutdown() {
		stopWasRequested = true;
		service.shutdown();
	}

}
