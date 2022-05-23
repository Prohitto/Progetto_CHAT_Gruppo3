import java.net.*;
import java.io.*;

public class ServerUDP {
	private DatagramSocket socket;
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public ServerUDP(int port) throws SocketException {
		socket = new DatagramSocket(port);
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public void run() {
		
		DatagramPacket answer;
		byte[] buffer = new byte[8192];
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		
		while(true) {
			try {
				socket.receive(request);
				
				answer = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
				
				socket.send(answer);
				
			}catch(IOException Exception) {
			}
			
		}
		
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void main (String[] args) {
		try {
			ServerUDP server = new ServerUDP(7);
			server.run();
		}catch (IOException exception) {
			System.err.println(exception);
		}
	}

}