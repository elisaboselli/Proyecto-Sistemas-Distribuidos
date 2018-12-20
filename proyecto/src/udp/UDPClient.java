package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import messages.Message;
import messages.MessageType;

public class UDPClient {

	public static void main(String args[]) {

		try {
			DatagramSocket socketUDP = new DatagramSocket();
			InetAddress hostServer = InetAddress.getByName(args[0]);
			int portServer = 6789;

			Message msg = new Message(MessageType.GET, "1", "holis");

			DatagramPacket request = new DatagramPacket(msg.toJSON().getBytes(), msg.toJSON().length(), hostServer,
					portServer);

			socketUDP.send(request);

			byte[] buffer = new byte[1000];
			DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			socketUDP.receive(response);

			byte[] data = new byte[response.getLength()];
			System.arraycopy(response.getData(), response.getOffset(), data, 0, response.getLength());
			String dataStr = new String(data);
			Message responseMsg = Message.fromJSON(dataStr);

			System.out.println("Response: " + responseMsg.toString());

			socketUDP.close();

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}
