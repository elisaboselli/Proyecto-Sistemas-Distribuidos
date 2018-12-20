package messages;

import com.google.gson.Gson;

public class Element {

	private int id;
	private int value;
	private int log;

	public Element(int _id, int _value, int _log) {
		this.id = _id;
		this.value = _value;
		this.log = _log;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int _id) {
		this.id = _id;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int _value) {
		this.value = _value;
	}

	public int getLog() {
		return this.log;
	}

	public void setLog(int _log) {
		this.log = _log;
	}

	@Override
	public String toString() {
		return "Element [id = " + this.id + ", value = " + this.value + ", log = " + this.log + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, Element.class);
	}

	public static Element fromJSON(String jsonStr) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, Element.class);
	}

}
