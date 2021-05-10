package main.modules;

import com.google.inject.AbstractModule;

import domain.mapping.MqttLagerAdapter;
import domain.mqtt.required.IAdapterNachricht;

public class MqttLagerModul extends AbstractModule {

	protected void configure() {
		bind(IAdapterNachricht.class).to(MqttLagerAdapter.class);
	}
	
}
