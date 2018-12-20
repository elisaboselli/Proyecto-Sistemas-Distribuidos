package messages;

import com.google.gson.Gson;

public class Message {

	private MessageType type;
	private String id;
	private String value;

	public Message(MessageType _type, String _id, String _value) {
		this.type = _type;
		this.id = _id;
		this.value = _value;
	}

	public MessageType getType() {
		return this.type;
	}

	public void setType(MessageType _type) {
		this.type = _type;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String _id) {
		this.id = _id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String _value) {
		this.value = _value;
	}

	@Override
	public String toString() {
		return "Message [type = " + this.type.name() + ", id = " + this.id + ", value = " + this.value + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, Message.class);
	}

	public static Message fromJSON(String jsonStr) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, Message.class);
	}

}