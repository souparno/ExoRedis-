package MyRedis;
import java.net.ServerSocket;

public class MyServer {
    
    private static int port=15000;
    private static int clientNumber = 1;
      /*********************************************************************** 
        The main method
     ***********************************************************************/  
    public static void main(String[] args){
        MyServer.log("The Server is running");
        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                new CommThreadPool(listener.accept(), clientNumber++).start();
            }
        }catch(Exception e){MyServer.log("Error: "+e);}
    }
    /*********************************************************************** 
       Logs a simple message.  In this case we just write the
        message to the server applications standard output.
     ***********************************************************************/
        private static void log(String message) {
            System.out.println(message);
        }
    
}
