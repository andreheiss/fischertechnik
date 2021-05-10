package main.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import domain.artificialClock.ClockService;
import domain.artificialClock.IClockService;

public class ClockModul extends AbstractModule {

	protected void configure() {
		bind(IClockService.class).to(ClockService.class);
		bind(ClockService.class).in(Singleton.class);
	}
	
}
