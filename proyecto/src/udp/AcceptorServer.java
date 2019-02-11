package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
//import java.util.concurrent.TimeUnit;

import messages.Element;
import messages.ServerMessage;
import messages.ServerMessageType;
import utils.JSONUtils;
import utils.PromisesSent;

public class AcceptorServer {

	public static void main(String args[]) {

		try {
			int port = Integer.parseInt(args[0]);
			int round = 0;										// Acceptor's current round
			String serverFile = port + "-ServerFile.txt";		// Acceptor's persistent file
			PromisesSent promises = new PromisesSent();			// Acceptor's promises

			@SuppressWarnings("resource")
			DatagramSocket socketUDP = new DatagramSocket(port);
			byte[] buffer = new byte[1000];

			JSONUtils jsonUtils = new JSONUtils();

			while (true) {
				DatagramPacket proposerRequest = new DatagramPacket(buffer, buffer.length);
				
				// Receive message from proposer
				socketUDP.receive(proposerRequest);

				ServerMessage requestMsg = jsonUtils.getServerMessage(proposerRequest);
				Element requestElement = requestMsg.getElement();

				System.out.println("Datagram received from host: " + proposerRequest.getAddress()
						+ " from remote port: " + proposerRequest.getPort());
				System.out.println("Request: " + requestMsg.toString());

				ServerMessage responseMsg = null;

				// Phase 1 (PREPARE/PROMISE)
				if (requestMsg.getType().equals(ServerMessageType.PREPARE)) {
					
					String jsonElements = jsonUtils.readJSONFile(serverFile);
					List<Element> elements = Element.fromJSONArray(jsonElements);
					Element elementFound = requestElement.findElementById(elements);
					
					if (elementFound == null || elementFound.getLog() > requestMsg.getRound()) {
						responseMsg = new ServerMessage(ServerMessageType.NACK, 0, null);
					} else {
						responseMsg = new ServerMessage(ServerMessageType.PROMISE, round, elementFound);
						promises.addPromise(elementFound.getId(), round);
						round ++;
					}
				}

				// Phase 2 (ACCEPT/ACCEPTED)
				if (requestMsg.getType().equals(ServerMessageType.ACCEPT)) {
					
					int lastPromise = promises.getPromiseRound(requestElement.getId());
					
					if (lastPromise < 0 || lastPromise > requestMsg.getRound()) {
						responseMsg = new ServerMessage(ServerMessageType.NACK, 0, null);
					} else {
						responseMsg = new ServerMessage(ServerMessageType.ACCEPTED, requestMsg.getRound(), requestElement);
					}
				}

				// Generate and send response
				DatagramPacket response = new DatagramPacket(responseMsg.toJSON().getBytes(),
						responseMsg.toJSON().length(), proposerRequest.getAddress(), proposerRequest.getPort());

				socketUDP.send(response);
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}