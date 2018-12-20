package messages;

import com.google.gson.Gson;

public class ClientMessage {

	private ClientMessageType type;
	private Element element;

	public ClientMessage(ClientMessageType _type, Element _element) {
		this.type = _type;
		this.element = _element;
	}

	public ClientMessageType getType() {
		return this.type;
	}

	public void setType(ClientMessageType _type) {
		this.type = _type;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element _element) {
		this.element = _element;
	}

	@Override
	public String toString() {
		return "Message [type = " + this.type.name() + ", value = " + this.element.toString() + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, ClientMessage.class);
	}

	public static ClientMessage fromJSON(String jsonStr) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, ClientMessage.class);
	}

}
