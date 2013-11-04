package feup.ei0902635.others;

public class Ticket {
	private String id = "";
	private String type = "";
	private String time = "";

	public Ticket(String i, String ty, String ti){
		id = i;
		type = ty;
		time = ti;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String toString() {
		return (getId() + " - " + getType() + " - " + getTime());
	}
}
