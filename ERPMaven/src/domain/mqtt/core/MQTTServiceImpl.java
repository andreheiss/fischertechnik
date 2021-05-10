package domain.mqtt.core;

import java.util.List;

import domain.mqtt.provided.IMQTTService;

public class MQTTServiceImpl implements IMQTTService {

	private _MQTT_Lernfabrik mqttClient;

	@Override
	public String produzieren(String Farbe) {
		mqttClient.placeOrder(Farbe);
		
		return null;
	}

	@Override
	public String getLocation(WorkPiece workpiece) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
