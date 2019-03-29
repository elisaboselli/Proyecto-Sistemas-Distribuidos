package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

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
			Scanner scan = new Scanner(System.in);
			boolean nextOp = true;

			System.out.println("\n---------- CLIENT SERVER ----------\n");

			while (nextOp) {
				String operation = getOperationFromInput(scan);
				int id, value;
				Element elem = null;
				ClientMessage msg = null;

				if (operation.equalsIgnoreCase("get")) {
					id = getIdFromInput(scan);
					elem = new Element(id, 0, 0);
					msg = new ClientMessage(ClientMessageType.GET, elem);
				}

				if (operation.equalsIgnoreCase("set")) {
					id = getIdFromInput(scan);
					value = getValueFromInput(scan);
					elem = new Element(id, value, 0);
					msg = new ClientMessage(ClientMessageType.SET, elem);
				}

				// elem = new Element(1, 1, 0);
				// msg = new ClientMessage(ClientMessageType.GET, elem);

				DatagramPacket request = new DatagramPacket(msg.toJSON().getBytes(), msg.toJSON().length(), hostServer,
						portServer);

				socketUDP.send(request);

				byte[] buffer = new byte[1000];
				DatagramPacket response = new DatagramPacket(buffer, buffer.length);
				socketUDP.receive(response);

				ClientMessage responseMsg = jsonUtils.getClientMessage(response);

				System.out.println("Response: " + responseMsg.toString());

				nextOp = getNextOperationFromInput(scan);

			}
			scan.close();
			socketUDP.close();
			System.out.println("\n---------- CLOSING CLIENT SERVER ----------\n");

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}

	private static String getOperationFromInput(Scanner scan) {
		String input = "";
		boolean inputCorrect = false;

		System.out.println("Enter the operation to be performed (GET / SET)");
		while (!inputCorrect) {
			input = scan.nextLine();
			inputCorrect = (input.equalsIgnoreCase("get") || input.equalsIgnoreCase("set"));
			if (!inputCorrect) {
				System.out.println("Please, enter a valid operation (GET / SET)");
			}
		}
		return input;
	}

	private static boolean getNextOperationFromInput(Scanner scan) {

		String input = "";
		boolean inputCorrect = false;
		scan.nextLine();
		System.out.println("Do you want to perform another operation ? (Y / N)");
		while (!inputCorrect) {
			input = scan.nextLine();
			if (input.equalsIgnoreCase("Y")) {
				return true;
			} else {
				if (input.equalsIgnoreCase("N")) {
					return false;
				}
			}
			if (!inputCorrect) {
				System.out.println("Please, enter a valid option (Y / N)");
			}
		}
		return false;
	}

	private static int getIdFromInput(Scanner scan) {
		System.out.println("Enter the element id");
		int id = scan.nextInt();
		return id;
	}

	private static int getValueFromInput(Scanner scan) {
		System.out.println("Enter the element new value");
		int value = scan.nextInt();
		return value;
	}
}
