package messages;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

	public static List<Element> fromJSONArray(String jsonStr) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<Element>>() {
		}.getType();
		return gson.fromJson(jsonStr, type);
	}

	public Element findElementById(List<Element> elements) {
		int elementId = this.id;
		for (Element element : elements) {
			if (element.getId() == elementId) {
				return element;
			}
		}
		return null;
	}

}
