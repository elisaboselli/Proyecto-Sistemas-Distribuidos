package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
//import java.util.concurrent.TimeUnit;

import messages.ServerMessage;
import utils.JSONUtils;

public class AcceptorServer {

	public static void main(String args[]) {

		try {
			int port = Integer.parseInt(args[0]);

			@SuppressWarnings("resource")
			DatagramSocket socketUDP = new DatagramSocket(port);
			byte[] buffer = new byte[1000];

			JSONUtils jsonUtils = new JSONUtils();

			while (true) {
				DatagramPacket proposerRequest = new DatagramPacket(buffer, buffer.length);

				socketUDP.receive(proposerRequest);

				ServerMessage requestMsg = jsonUtils.getServerMessage(proposerRequest);

				System.out.println("Datagram received from host: " + proposerRequest.getAddress()
						+ " from remote port: " + proposerRequest.getPort());
				System.out.println("Request: " + requestMsg.toString());

				// Generate and send response
				DatagramPacket response = new DatagramPacket(proposerRequest.getData(), proposerRequest.getLength(),
						proposerRequest.getAddress(), proposerRequest.getPort());

				socketUDP.send(response);
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}