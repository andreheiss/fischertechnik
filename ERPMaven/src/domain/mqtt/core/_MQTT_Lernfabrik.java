package domain.mqtt.core;

import java.time.Instant;
import java.util.List;

import org.eclipse.paho.client.mqttv3.*;
import com.google.gson.*;
import com.google.inject.Inject;

import domain.mqtt.required.ILagerNachricht;

public class _MQTT_Lernfabrik implements MqttCallback {

	private ILagerNachricht iln;

// MQTT Clients (1 for send, 2 for receive)
	private MqttClient client;

//	states of parts the Lernfabrik
	private Stock stock;
	private Order order;
	private HBWstate hbwstate;
	private HBWack hbwack;
	private MPOstate mpostate;
	private MPOack mpoack;
	private SLDstate sldstate;
	private SLDack sldack;
	private VGRstate vgrstate;
	private VGRtrigger vgrtrigger;
	private DSI dsi;
	private DSO dso;
	private NFC nfc;
	private Cam cam;

	private ActiveWorkPiece awpout = new ActiveWorkPiece("OUT");
	private ActiveWorkPiece awpin = new ActiveWorkPiece("IN");

	private WorkPieceHistory wph = new WorkPieceHistory();

	private String ip = "192.168.0.10";
	private String port = ":1883";

	private final String quit = "f/o/state/ack";
	private final String alert = "i/alert";
	private final String broadcast = "i/broadcast";

	private final String hbwAck = "fl/hbw/ack";
	private final String hbwState = "f/i/state/hbw";

	private final String mpoAck = "fl/mpo/ack";
	private final String mpoState = "f/i/state/mpo";

	private final String sldAck = "fl/sld/ack";
	private final String sldState = "f/i/state/sld";

	private final String vgrState = "f/i/state/vgr";
	private final String vgrTrigger = "fl/vgr/do";

	private final String dsiState = "f/i/state/dsi";
	private final String dsoState = "f/i/state/dso";

	private final String iStock = "f/i/stock";

	private final String iOrder = "f/i/order";
	private final String oOrder = "f/o/order";

	private final String iNfc = "f/i/nfc/ds";
	private final String oNfc = "f/o/nfc/ds";

	private final String iCam = "i/cam";
	private final String cCam = "c/cam";
	private final String ptu = "o/ptu";
	private final String ptupos = "i/ptu/pos";

	private final String link = "c/link";

	@Inject
	public _MQTT_Lernfabrik(ILagerNachricht iln) {
		this.iln = iln;

	}

	public _MQTT_Lernfabrik() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		new _MQTT_Lernfabrik().launch();
	}

	public void launch() {

		try {
			client = new MqttClient("tcp://" + ip + port, "ERPSystem");
			client.connect();
			client.setCallback(this);

//			client.subscribe(quit);
//			client.subscribe(alert);
//			client.subscribe(broadcast);
//
//			client.subscribe(hbwState);
			client.subscribe(hbwAck);
//
			client.subscribe(iStock);
//
//			client.subscribe(vgrState);
			client.subscribe(vgrTrigger);
//
//			client.subscribe(mpoState);
			client.subscribe(mpoAck);
//
//			client.subscribe(sldState);
			client.subscribe(sldAck);
//
//			client.subscribe(dsiState);
//			client.subscribe(dsoState);
//
			client.subscribe(iOrder);
//			client.subscribe(oOrder);
//
			client.subscribe(iNfc);
//			client.subscribe(oNfc);
//
//			client.subscribe(iCam);
//			client.subscribe(cCam);
//			client.subscribe(ptu);
//			client.subscribe(ptupos);
//			
//			client.subscribe(link);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// messages for subscribed topics are processed here

		// System.out.println(topic + " : " + message);

		switch (topic) {

		case iStock:
			updateStock(message.toString());
			break;

		case iOrder:
			updateOrder(message.toString());
			break;

		case hbwAck:
			updateHBWack(message.toString());
			break;

		case hbwState:
			updateHBWstate(message.toString());
			break;

		case mpoAck:
			updateMPOack(message.toString());
			break;

		case mpoState:
			updateMPOstate(message.toString());
			break;

		case sldAck:
			updateSLDack(message.toString());
			break;

		case sldState:
			updateSLDstate(message.toString());
			break;

		case vgrTrigger:
			updateVGRtrigger(message.toString());
			break;

		case vgrState:
			updateVGRstate(message.toString());
			break;

		case dsiState:
			updateDSI(message.toString());
			break;

		case dsoState:
			updateDSO(message.toString());
			break;

		case iNfc:
			updateNFC(message.toString());
			break;

		case iCam:
			updateCam(message.toString());
			break;
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

	// functions and methods for message processing

	public void placeOrder(String color) {

		try {
			client.publish(oOrder, ("{\"type\":\"" + color + "\",\"ts\":\"" + Instant.now() + "\"}").getBytes(), 2,
					true);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void activateNFC(String command) {
		try {
			client.publish(oNfc, ("{\"cmd\":\"" + command + "\",\"ts\":\"" + Instant.now() + "\"}").getBytes(), 2,
					true);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void updateStock(String message) {

		System.out.println("Stock");

		Stock tempStock = stock;
		ActiveWorkPiece tempawp = new ActiveWorkPiece();

		Gson gson = new Gson();
		stock = gson.fromJson(message, Stock.class);

		if ((tempStock != null) && (tempStock.getWorkpieces().size() != stock.getWorkpieces().size())) {
			List<WorkPiece> bigList, littleList;
			Stock big;

			System.out.println("Stock Änderung");

			if (tempStock.getWorkpieces().size() > stock.getWorkpieces().size()) {
				bigList = tempStock.getWorkpieces();
				big = tempStock;
				littleList = stock.getWorkpieces();
				awpout.setLocation("HBW");
				awpout.setState("BOOKED_OUT");
				tempawp = awpout;


			} else {
				bigList = stock.getWorkpieces();
				big = stock;
				littleList = tempStock.getWorkpieces();
				awpin.setLocation("HBW");
				awpin.setState("STORED");
				tempawp = awpin;

			}

			int j = 0;

			for (int i = 0; i < littleList.size(); i++) {
				if (!bigList.get(i).getId().equals(littleList.get(i).getId())) {
					tempawp.setWorkpiece(bigList.get(i));
					j = 1;
					break;
				}
			}
			if (j == 0) {
				tempawp.setWorkpiece(bigList.get(bigList.size() - 1));
			}

			tempawp.setStockplace(big.getStockplaceOf(tempawp.getWorkpiece()));
			// Lagerplatz und Art des aktiven Teils
			System.out.println("AWP zugeordnet nach Stockänderung");

			if ((wph.getEntrysOf(tempawp.getWorkpiece().getId()).size() == 0)
					|| !(wph.getLastEntryOf(tempawp.getWorkpiece().getId()).getStates().get(0).equals("STORED"))
					&& !(wph.getLastEntryOf(tempawp.getWorkpiece().getId()).getStates().get(0).equals("BOOKED_OUT"))) 
					{
				wph.addHistoryEntryOfAWP(tempawp, stock.getTs());
			}
			if (tempawp.getPostingdirection().equals(awpin.getPostingdirection())) {
				
//				iln.einlagerInfo(awpin.getWorkpiece(), awpin.getStockplace());
//				Benachrichtigung über Buchung nur zu Testen auskommentiert
				
				awpin.clearAWP();
			}
			if (tempawp.getPostingdirection().equals(awpout.getPostingdirection())) {
				
//				iln.auslagerInfo(awpout.getStockplace());
//				Benachrichtigung über Buchung nur zu Testen auskommentiert
				
				awpout.clearStockplace();
			}
		}
	}

	public void updateOrder(String message) {

		System.out.println("Order");

		Gson gson = new Gson();
		order = gson.fromJson(message, Order.class);

		switch (order.getState()) {

		case "ORDERED":
			awpout.setState("ORDERED");
			awpout.setLocation("HBW");

			if ((awpout.getWorkpiece() == null) || ((!wph.getLastEntryOf(awpout.getWorkpiece().getId()).getStates()
					.get(0).equals("ORDERED"))
					&& (!wph.getLastEntryOf(awpout.getWorkpiece().getId()).getStates().get(0).equals("BOOKED_OUT")))) {
				wph.addHistoryEntryOfAWP(awpout, order.getTs());
			}
			break;
			
		case "IN_PROCESS":
			awpout.setState("IN_PROCESS");
			break;
			
		case "SHIPPED":
			if ((awpout.getLocation() != null) && (!awpout.getLocation().equals("SHIPPED"))) {
				awpout.setLocation("SHIPPED");
				awpout.setPostingdirection("SHIPPED");
				awpout.setState("SHIPPED");
				wph.addHistoryEntryOfAWP(awpout, order.getTs());
				awpout.clearAWP();
			}
			break;
		}
	}

	public void updateHBWack(String message) {

		System.out.println("HBWack");

		Gson gson = new Gson();
		hbwack = gson.fromJson(message, HBWack.class);

		// WorkPieceHistoryEntry lastawpinentry =
		// wph.getLastEntryOf(awpin.getWorkpiece().getId());
		ActiveWorkPiece awptemp = new ActiveWorkPiece();
		String statetemp = new String();

		if (!(awpin.getWorkpiece() == null)
				&& (wph.getLastEntryOf(awpin.getWorkpiece().getId()).getStates().get(0).equals("CONTAINER_FETCHED"))) {
			awptemp = awpin;
			statetemp = "TRANSPORTED_TO_HBW";
		} else {
			awptemp = awpout;
			statetemp = "TRANSPORTED_TO_MPO";
		}

		switch (hbwack.getCode()) {
		case 1:
			awptemp.setLocation("VGR");
			awptemp.setState(statetemp);
			wph.addHistoryEntryOfAWP(awptemp, hbwack.getTs());
			break;
		case 2:
			break;
		}

	}

	public void updateHBWstate(String message) {

		Gson gson = new Gson();
		hbwstate = gson.fromJson(message, HBWstate.class);

	}

	public void updateMPOack(String message) {

		System.out.println("MPOack");
		Gson gson = new Gson();
		mpoack = gson.fromJson(message, MPOack.class);

		switch (mpoack.getCode()) {
		case 1:
			break;

		case 2:
			awpout.setLocation("SLD");
			awpout.setState("PROCESSED");
			wph.addHistoryEntryOfAWP(awpout, mpoack.getTs());
			break;
		}
	}

	public void updateMPOstate(String message) {

		Gson gson = new Gson();
		mpostate = gson.fromJson(message, MPOstate.class);

	}

	public void updateSLDack(String message) {

		System.out.println("SLDack");

		Gson gson = new Gson();
		sldack = gson.fromJson(message, SLDack.class);

		switch (sldack.getCode()) {
		case 1:
			break;

		case 2:
			awpout.setLocation("VGR");
			awpout.setState("TRANSPORTED_TO_DSO");
			wph.addHistoryEntryOfAWP(awpout, sldack.getTs());
			break;
		}
	}

	public void updateSLDstate(String message) {

		Gson gson = new Gson();
		sldstate = gson.fromJson(message, SLDstate.class);

	}

	public void updateVGRtrigger(String message) {

		System.out.println("VGRtrigger");

		Gson gson = new Gson();
		vgrtrigger = gson.fromJson(message, VGRtrigger.class);

		switch (vgrtrigger.getCode()) {
		case 1:
			awpin.setLocation("VGR");
			awpin.setWorkpiece(vgrtrigger.getWorkpiece());
			awpin.setState("CONTAINER_FETCHED");
			wph.addHistoryEntryOfAWP(awpin, vgrtrigger.getTs());
			break;

		case 2:
			awpin.setLocation("HBW");
			awpin.setState("TRANSPORTED_TO_STOCKPLACE");
			wph.addHistoryEntryOfAWP(awpin, vgrtrigger.getTs());
			break;

		case 3:
			awpout.setLocation("HBW");
			break;

		case 4:
			awpout.setLocation("VGR");
			break;

		case 7:
			awpout.setLocation("MPO");
			wph.addHistoryEntryOfAWP(awpout, vgrtrigger.getTs());
			awpout.clearAWP();
			break;

		case 8: // SLD Start, fehlerhaft Lernfabrik
			break;

		}

	}

	public void updateVGRstate(String message) {

		Gson gson = new Gson();
		vgrstate = gson.fromJson(message, VGRstate.class);

	}

	public void updateDSI(String message) {

		Gson gson = new Gson();
		dsi = gson.fromJson(message, DSI.class);

	}

	public void updateDSO(String message) {

		Gson gson = new Gson();
		dso = gson.fromJson(message, DSO.class);

	}

	public void updateNFC(String message) {

		System.out.println("NFC");

		Gson gson = new Gson();
		nfc = gson.fromJson(message, NFC.class);

		if (nfc.getHistory() == null) {
			awpin.setWorkpiece(nfc.getWorkpiece());
		}

		else {
			// (nfc.getHistory().size() == 2)
			if (nfc.getHistory().get(nfc.getHistory().size() - 1).getCode() == 200) {
				for (int i = 0; i < 2; i++) {

					if ((awpin.getLocation() == null)
							|| ((!awpin.getLocation().equals("NFC")) && ((!awpin.getLocation().equals("VGR"))))) {
						switch (nfc.getHistory().get(i).getCode()) {
						case 100:
							awpin.setLocation("DSI");
							awpin.setState("RECEIVED");
							awpin.setWorkpiece(nfc.getWorkpiece());
							wph.addHistoryEntryOfAWP(awpin, nfc.getHistory().get(i).getTs());
							break;
						case 200:
							awpin.setLocation("NFC");
							awpin.setState("SCANNED");
							awpin.setWorkpiece(nfc.getWorkpiece());
							wph.addHistoryEntryOfAWP(awpin, nfc.getHistory().get(i).getTs());
							break;
						}
					}
				}
			}
			// (nfc.getHistory().size() >= 4)
			if ((nfc.getHistory().get(nfc.getHistory().size() - 1).getCode() == 800)
					&& (!awpout.getLocation().equals("DSO"))) {
				awpout.setLocation("NFC");
				awpout.setWorkpiece(nfc.getWorkpiece());
				awpout.setState("SCANNED");
				wph.addHistoryEntryOfAWP(awpout, nfc.getHistory().get(nfc.getHistory().size() - 1).getTs());
				awpout.setLocation("DSO");
				awpout.setState("TRANSPORTED_TO_OUT");
				wph.addHistoryEntryOfAWP(awpout, nfc.getTs());
			}
		}

	}

	public void updateCam(String message) {

		Gson gson = new Gson();
		cam = gson.fromJson(message, Cam.class);

	}
}
