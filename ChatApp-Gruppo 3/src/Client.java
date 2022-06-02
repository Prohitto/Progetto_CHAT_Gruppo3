import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        final Socket clientSocket;      // socket per ricevere e mandare messaggi al server
        final BufferedReader in;        // oggetto per ricevere messaggi dal server
        final PrintWriter out;          // oggetto per scrivere messaggi da mandare al server
        final Scanner sc = new Scanner(System.in);  // oggetto per leggere gli input da tastiera
//-----------------------------------------------------------------------------------------------------------------------//

        try {
            clientSocket = new Socket("127.0.0.1",5000);    //inizializzazione del socket sulla porta 5000 dell' ip 127.0.0.1 (local host)
            out = new PrintWriter(clientSocket.getOutputStream());    //inizializzazione dell oggetto out per mandare i messaggi tramite clientSocket
            //inizializzazione dell' oggetto in per ricevere i messaggi ricevuti da clientSocket convertendo il messaggio da stringa a bytes tramite InputStreamReader
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//-----------------------------------------------------------------------------------------------------------------------//

            //creiamo due thread che verranno eseguiti contemporaneamente così che si potrà sia ricever che mandare allo stesso tempo

            Thread sender= new Thread(new Runnable() {      //thread per mandare messaggi
                String msg;     //variabile che conterrà il messaggio da mandare
                @Override       //annotazione per sovrascrivere il metodo run
                public void run() {
                    while(true){
                        msg = sc.nextLine();    //input del messaggio da tastiera
                        out.println(msg);       //scrive il contenuto di msg in clientSocket
                        out.flush();            //manda il contenuto di clientScocket
                    }
                }
            });
            sender.start();     //avvia il thread sender
//-----------------------------------------------------------------------------------------------------------------------//

            Thread receiver = new Thread(new Runnable() {
                String msg ;    //variabile che conterrà il messaggio del Client
                @Override       //annotazione per sovrascrivere il metodo run
                public void run() {
                    try {
                        msg = in.readLine();    //legge il messaggio mandato al clientSocket
                        //finché il client è ancora connesso al server
                        while(msg!=null){
                            System.out.println("Server : "+msg);    //stampa il messaggio ricevuto dal Client
                            msg = in.readLine();                    //legge il messaggio mandato al clientSocket per lasciare aperta la chat
                        }
                        //in caso msg == null il client si è disconnesso
                        System.out.println("Server fuori servizio");

                        //si chiude tutto
                        out.close();
                        clientSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver .start();      //avvia il thread receiver
//-----------------------------------------------------------------------------------------------------------------------//

    }catch (IOException e){
        e.printStackTrace();
        }
    }
}
