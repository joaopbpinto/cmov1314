package feup.ei0902635.others;

public class Ticket {
	private String id;
	private String type;
	private String time;
	private String idb;

	public Ticket(String i, String ty, String ti, String ib){
		id = i;
		type = ty;
		time = ti;
		idb = ib;
	}
	
	public Ticket(String i){
		id = i;
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
	
	public String getIdb() {
		return idb;
	}

	public void setIdb(String idb) {
		this.idb = idb;
	}

	public String toString() {
		if(type.isEmpty() || time.isEmpty())
			return (getId() + " - T" + getType());
		else
			return (getIdb() + ": " + getId() + " - T" + getType() + " - " + getTime());
	}
}
