package messages;

import com.google.gson.Gson;

public class ServerMessage {

	private ServerMessageType type;
	private int round;
	private Element element;

	public ServerMessage(ServerMessageType _type, int _round, Element _element) {
		this.type = _type;
		this.round = _round;
		this.element = _element;
	}

	public ServerMessageType getType() {
		return this.type;
	}

	public void setType(ServerMessageType _type) {
		this.type = _type;
	}

	public int getRound() {
		return this.round;
	}

	public void setRound(int _round) {
		this.round = _round;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element _element) {
		this.element = _element;
	}

	@Override
	public String toString() {
		return "ServerMessage [type = " + this.type.name() + ", round = " + this.round + ", value = "
				+ this.element.toString() + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, ServerMessage.class);
	}

	public static ServerMessage fromJSON(String jsonStr) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, ServerMessage.class);
	}

}
