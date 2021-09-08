package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Client {

	private String clientId;
	
	private ArrayList<LocalDateTime> accessTimeList;

	public Client(String clientId) {
		super();
		this.clientId = clientId;
		this.accessTimeList = new ArrayList<LocalDateTime>();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public ArrayList<LocalDateTime> getAccessTimeList() {
		return accessTimeList;
	}

	public void setAccessTimeList(ArrayList<LocalDateTime> accessTimeList) {
		this.accessTimeList = accessTimeList;
	}
	
}
