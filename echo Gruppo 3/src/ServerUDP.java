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
		String mess;
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br =  new BufferedReader(in);
		
		while(true) {
			try {
				socket.receive(request);
				System.out.println(request.getData());
				
				//answer = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
				
				mess = br.readLine();
				buffer = mess.getBytes("UTF-8");
				
				answer = new DatagramPacket(buffer, buffer.length);
				socket.send(answer);
				
			}catch(IOException Exception) {
			}
			
		}
		
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void main (String[] args) {
		try {
			ServerUDP server = new ServerUDP(5000);
			server.run();
		}catch (IOException exception) {
			System.err.println(exception);
		}
	}

}