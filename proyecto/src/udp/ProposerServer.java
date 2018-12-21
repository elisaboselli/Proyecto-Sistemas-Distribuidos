package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import messages.ClientMessage;
import utils.Host;
import utils.JSONUtils;

public class ProposerServer {

	// final static String SERVERS = "textFiles/allServers.txt";
	final static String SERVERS = "textFiles/serversTest.txt";
	final static int SERVERS_QTY = 2;
	final static int QUORUM = (SERVERS_QTY / 2) + 1;
	final static int WAIT_TIME = 60000;

	public static void main(String args[]) {

		try {
			@SuppressWarnings("resource")
			DatagramSocket socketUDP = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];

			JSONUtils jsonUtils = new JSONUtils();

			String jsonHosts = jsonUtils.readJSONFile(SERVERS);
			List<Host> hosts = Host.fromJSONArray(jsonHosts);

			while (true) {
				DatagramPacket clientRequest = new DatagramPacket(buffer, buffer.length);

				socketUDP.receive(clientRequest);

				ClientMessage requestMsg = jsonUtils.getClientMessage(clientRequest);

				System.out.println("Datagram received from host: " + clientRequest.getAddress() + " from remote port: "
						+ clientRequest.getPort());
				System.out.println("Request: " + requestMsg.toString());

				// Broadcast
				for (Host host : hosts) {
					DatagramPacket acceptorsRequest = new DatagramPacket(clientRequest.getData(),
							clientRequest.getLength(), host.getAddress(), host.getPort());
					socketUDP.send(acceptorsRequest);
				}

				int responses = 1;
				long timeout = System.currentTimeMillis() + WAIT_TIME;

				while (responses < QUORUM && System.currentTimeMillis() < timeout) {
					DatagramPacket acceptorResponse = new DatagramPacket(buffer, buffer.length);
					socketUDP.receive(acceptorResponse);
					// ServerMessage responseMsg =
					// jsonUtils.getServerMessage(acceptorResponse);
					responses++;
				}

				if (responses < QUORUM)
					System.out.println("TimeOut. Received responses: " + responses);
				else
					System.out.println("Quorum OK. Received responses: " + responses);

				// Respuesta al cliente
				/*
				 * DatagramPacket response = new
				 * DatagramPacket(request.getData(), request.getLength(),
				 * request.getAddress(), request.getPort());
				 * 
				 * socketUDP.send(response);
				 */
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}