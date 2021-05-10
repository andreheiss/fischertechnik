package domain.mqtt.core;

public class WorkPiece {

    

	private String id;
    private String state;
    private String type;
    
    public WorkPiece(String id, String state, String type) {
		this.id = id;
		this.state = state;
		this.type = type;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
