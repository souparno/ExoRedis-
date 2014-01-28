package MyRedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommThreadPool extends Thread{
    
    private Socket socket;
    private int clientNumber;
    private BufferedReader in;
    private PrintWriter out;
    
    CommThreadPool(Socket socket,int clientNumber) throws IOException{
         this.socket = socket;
         this.clientNumber = clientNumber;
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         out = new PrintWriter(socket.getOutputStream(), true);
         log("New connection with client# " + clientNumber + " at " + socket);   
     }
    /*********************************************************************** 
       Logs a simple message.  In this case we just write the
        message to the server applications standard output.
     ***********************************************************************/
        private void log(String message) { System.out.println(message); }
    /*********************************************************************** 
       Logs a simple message.  In this case we just write the
        message to the server applications standard output.
     ***********************************************************************/
        public void run() {
          try{
                // Send a welcome message to the client.
                out.println("Connected To Localhost");
                out.flush();
                // Get messages from the client, line by line;
                while (true) {
                 String input = in.readLine().trim();
                 // freeing the thread if the client has send a 0
                 if (input.equalsIgnoreCase("0")) break;
                 decodeRequest(input);
                }// end of while
          }catch(Exception e){ log("Error handling client# " + clientNumber + ": " + e);}
          finally {try { socket.close(); } catch (IOException e) { log("Couldn't close a socket, what's going on?"+e); } 
          log("Connection with client# " + clientNumber + " closed");}
       }
     /*******************************************************************
      method to decode the request
    *********************************************************************/
        public void decodeRequest(String input){
            String Comand_type=input.substring(0,input.indexOf(" ")).toUpperCase();
            String Response="";
            switch (Comand_type) {
                case "SET":
                    {
                        String Variable_name=input.substring(input.indexOf(" ")+1,input.lastIndexOf(" "));
                        String Value=input.substring(input.lastIndexOf(" ")+1);
                        Response=new Command()._set(Variable_name, Value);
                        break;
                    }
                case "GET":
                    {
                        String Variable_name=input.substring(input.indexOf(" ")+1);
                        Response=new Command()._get(Variable_name);
                        break;
                    }
                case "SETBIT":
                    {
                      try{
                        String command[]=input.split(" ");
                        String variable=command[1];
                        int offset=Integer.parseInt(command[2]);
                        int bit=Integer.parseInt(command[3]);
                        Response=new Command()._set_offset(variable, offset, bit);
                      }
                      catch(Exception e){ Response="Error :"+e; }
                    }
                case "GETBIT":
                {
                    try{
                        String command[]=input.split(" ");
                        String variable=command[1];
                        int offset=Integer.parseInt(command[2]);
                        Response=new  Command()._get_offset(variable, offset);
                        
                    }catch(Exception e){ Response="Error :"+e; }
                }
                    
            }
           out.println(Response);
        }   
}// End of class
