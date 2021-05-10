package main.modules;

import com.google.inject.AbstractModule;

import domain.mapping.MqttLagerAdapter;
import domain.mqtt.required.ILagerNachricht;

public class MqttLagerModul extends AbstractModule {

	protected void configure() {
		bind(ILagerNachricht.class).to(MqttLagerAdapter.class);
	}
	
}
