package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import messages.ClientMessage;
import messages.ClientMessageType;
import messages.Element;
import utils.JSONUtils;

public class Client {

	public static void main(String args[]) {

		try {
			DatagramSocket socketUDP = new DatagramSocket();
			InetAddress hostServer = InetAddress.getByName(args[0]);
			int portServer = 6789;

			JSONUtils jsonUtils = new JSONUtils();

			Element elem = new Element(1, 1, 0);
			ClientMessage msg = new ClientMessage(ClientMessageType.GET, elem);

			DatagramPacket request = new DatagramPacket(msg.toJSON().getBytes(), msg.toJSON().length(), hostServer,
					portServer);

			socketUDP.send(request);

			byte[] buffer = new byte[1000];
			DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			socketUDP.receive(response);

			ClientMessage responseMsg = jsonUtils.getClientMessage(response);

			System.out.println("Response: " + responseMsg.toString());

			socketUDP.close();

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}
