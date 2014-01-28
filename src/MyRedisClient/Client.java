package MyRedisClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    
    private static BufferedReader in;
    private static PrintWriter out;
    private static String serverAddress = "127.0.0.1";
    private static int port=15000;
    
/*********************************************************************** 
       Logs a simple message.  In this case we just write the
        message to the server applications standard output.
***********************************************************************/   
  public static void connectToServer() throws IOException {
        log("Trying " + serverAddress +"...");    
        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress,port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        // Consume the initial welcoming messages from the server
        log(in.readLine());
    }
/*********************************************************************** 
  Sends a request to the server
***********************************************************************/
public static void sendRequest()throws IOException{
  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
  System.out.println("0 to quit");
  String request;
  do{
      request=br.readLine();
      out.println(request);
      String response;
      response = in.readLine();
      log(response);
  }while(request.equalsIgnoreCase("0")!=true);
  System.out.println("Connection Closed");   
}  
/*********************************************************************** 
  Logs a simple message.  In this case we just write the
  message to the server applications standard output.
***********************************************************************/
private static void log(String message) {
   System.out.println(message);
}    
/*********************************************************************** 
   The main method
***********************************************************************/        
public static void main(String args[])throws IOException{
    Client.connectToServer();
    Client.sendRequest();
        
}

}
