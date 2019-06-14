package be.ipeters.designpatterns.observer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;

public class TemperatureSensor {
	private float currentReading = 67;
	private boolean stopWasRequested = false;
	private final ExecutorService service = Executors.newCachedThreadPool();
	private final List<TemperatureSensorListener> listeners = Lists.newArrayList();
	
	public TemperatureSensor() {
		service.submit(new Runnable() {
			@Override
			public void run() {
				while(!stopWasRequested) {
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {
						// do nothing
					}
					currentReading--; // aftellen
					fireTemperaturChangeEvent();
					
					System.out.println("Current temp is "+currentReading);
				}
			}

			private void fireTemperaturChangeEvent() {
				for(TemperatureSensorListener listener:listeners) {
					listener.onReadingChange();
				}
			}
			
		});
	}
	
	public void addListener(TemperatureSensorListener listener) {
		listeners.add(listener);
	}
	public float getCurrentReading() {
		return currentReading;
	}
	
	public void shutdown() {
		stopWasRequested = true;
		service.shutdown();
	}

}
