package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;

import messages.ClientMessage;
import messages.ServerMessage;

public class JSONUtils {

	public JSONUtils() {
	}

	public String readJSONFile(String fileName) {
		String jsonStr = "";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				jsonStr = jsonStr.concat(line);
			}
			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return jsonStr;
	}

	private String processRequest(DatagramPacket request) {
		byte[] data = new byte[request.getLength()];
		System.arraycopy(request.getData(), request.getOffset(), data, 0, request.getLength());
		return new String(data);
	}

	public ClientMessage getClientMessage(DatagramPacket request) {
		String jsonStr = processRequest(request);
		return ClientMessage.fromJSON(jsonStr);
	}

	public ServerMessage getServerMessage(DatagramPacket request) {
		String jsonStr = processRequest(request);
		return ServerMessage.fromJSON(jsonStr);
	}

}
