import java.net.*;
import java.io.*;

public class ClientUDP {
	private DatagramSocket socket;
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public ClientUDP () throws SocketException {
		socket = new DatagramSocket();
		socket.setSoTimeout(1000);		//tempo chiusura del socket in millisecondi
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public void closeSocket() {
		socket.close();
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	
	public String sendAndReceive( String request , String host , int port ) throws UnknownHostException, IOException, SocketTimeoutException {
		
		byte[] buffer;		//variabile contentitore del messaggio
		DatagramPacket datagram;		//pacchetto contenete le informazioni del pacchetto da inviare
		String answer;		//variabile contente la risposta del server
		
		// indirizzo IP del destinatario del datagram
		InetAddress address = InetAddress.getByName(host);
		
		if( socket.isClosed() ) {
			throw new IOException();
		}
	//-----------------------------------------------------------------------------------------------------------------------//
		buffer = request.getBytes("UTF-8");		//per imporre il charset UTF-8
		
		datagram = new DatagramPacket( buffer , buffer.length , address , port );
		
		socket.send(datagram);
		
		socket.receive(datagram);
	//-----------------------------------------------------------------------------------------------------------------------//
		//controllo che il pacchetto arrivi dal server 
		if(datagram.getAddress().equals(address) && datagram.getPort() == port ) {
			
			answer = new String (datagram.getData() , 0 ,datagram.getLength() , "UTF-8" );
		
		}else{
				throw new SocketTimeoutException();		
			 }
		
	  //------------------------------------//
		if(answer.equals("Hello There") ) {
			answer = "General Kenobi"; }
	  //------------------------------------//
		
		return answer ;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void main(String args[]) throws IOException {
		
		String IPaddress;
		int UDPport;
		String request , answer="";
		ClientUDP client;
		
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br =  new BufferedReader(in);
		
		IPaddress = "127.0.0.1";		//ip di localhost
		UDPport = 5000;					//porta UDP
		request = br.readLine();      	//messaggio da mandare
		
		try {
			client = new ClientUDP();
			answer = client.sendAndReceive(request, answer, UDPport);
			System.out.println("\n" +answer);
			client.closeSocket();
			
		}catch(SocketException exception){
			System.err.print(exception);
		}catch(UnknownHostException exception){
			System.err.print(exception);
		}catch(SocketTimeoutException exception){
			System.err.print(exception);
		}catch(IOException exception){
			System.err.print(exception);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}