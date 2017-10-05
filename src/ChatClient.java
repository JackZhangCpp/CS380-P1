
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public final class ChatClient {
	private static boolean Disconnected = true; 
	public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182", 38001)) {
        //Runnable thread for server messages.
        	Runnable serv = () ->{
        	  try	 {
        		  String message;
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");

            BufferedReader br = new BufferedReader(isr);
            while(Disconnected == true)
      	  	{
            message = br.readLine();
            if(message.equals("Name in use.")|| message.equals("Connection idle for 1 minute, closing connection."))
            	{
             	 System.out.printf("%s%nDisconnected",message);
            	 Disconnected=false;
            	}   
            else
                System.out.println(message);
      	  	}
        	  		}catch( Exception e) {return;}

        	  };
        	Thread messageIn= new Thread(serv); //Thread creation and destination to start
        	messageIn.start();					//Thread start
        	
        	String str;
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            System.out.print("Enter username: "); 
            BufferedReader svin= new BufferedReader(new InputStreamReader(System.in));  
           
            while((str=svin.readLine())!=null) {
            	if(str.equals("exit")) //check if client want to leave before message gets sent
            		{break;}
            	else 
            		{ out.println(str);  }
            	if(Disconnected == false)// checks for disconnection and stops program
            		{break;}
            	}
        	}
        
       
    }

}
