package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
//import java.util.concurrent.TimeUnit;

import messages.Message;

public class UDPServer {

	public static void main(String args[]) {

		try {
			@SuppressWarnings("resource")
			DatagramSocket socketUDP = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];

			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);

				socketUDP.receive(request);

				byte[] data = new byte[request.getLength()];
				System.arraycopy(request.getData(), request.getOffset(), data, 0, request.getLength());
				String dataStr = new String(data);
				Message requestMsg = Message.fromJSON(dataStr);

				System.out.println("Datagram received from host: " + request.getAddress() + " from remote port: "
						+ request.getPort());
				System.out.println("Request: " + requestMsg.toString());

				DatagramPacket response = new DatagramPacket(request.getData(), request.getLength(),
						request.getAddress(), request.getPort());

				// System.out.println("Sleeping");
				// TimeUnit.SECONDS.sleep(30);
				// System.out.println("AWAKE");

				socketUDP.send(response);
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} /*
			 * catch (InterruptedException e){ System.out.println("IE: " +
			 * e.getMessage()); } catch (ParseException e){
			 * System.out.println("PE: " + e.getMessage()); }
			 */
	}
}