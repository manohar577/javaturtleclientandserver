/*
* 
* File Name:  TurtleServer.java
* Author1: Harika Hari (NAU ID: hh453@nau.edu)
* Author2: Caitlin Barrett (NAU ID: cb2693@nau.edu)
*  
* Description: THis is a GUI based java code that connects with a client to serve the user needs. 
* It interacts with the client to server the multiple requests by the client.
* THe GUI Server is intelligent enough to handle the errors and update user appropriately with messages.
* The functionality of the TurtleServer.java is to accept 
* the input from the client and process it and display the result on the white board.
* 
* Also TurtleServer.java is  multi threaded to accept requests from 
* multiple clients and process the clients requests and respond 
* to multiple clients simultaneously.
*/
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class TurtleServer implements ActionListener,Runnable {

	Socket socket;	
	Line2D.Double line;
	String direction;
	int length;
	static LinesComponent lineComponent;

    TurtleServer (Socket socket) {
    this.socket = socket;
    }

	
    TurtleServer() {
    	
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



	public static void main(String[] args) {

		lineComponent =  new LinesComponent();
		lineComponent.initialize(lineComponent);
		new TurtleServer();
		try {
			// ServerSocket object to listen multiple clients requests on a specific port number
			 ServerSocket s_socket = new ServerSocket(23657);
			 System.out.println("Server socket is binded and listening to incoming requests...");
			 while (true) {
				//socket object to listen and bind the socket in order to listen multiple clients requests
				 Socket socket = s_socket.accept();
				new Thread(new TurtleServer(socket)).start();	 
				System.out.println("A client is Connected..."+"\n");
			 }
		} catch (IOException e) {
			System.out.println("The socket is occupied. Unable to accept the input...");
		}
		
	}
	
	@Override
	public void run() {
		Scanner input;
		PrintStream outputStream = null;
		String s;
		try { 
		 //scanner object with input stream parameter to read request message from the client		
		input = new Scanner(socket.getInputStream());
		 while(input.hasNext()) {		
		 //read the data from the stream
		 s = input.nextLine();
		 //Create a print stream object to pass on the data to client 
		 outputStream = new PrintStream(socket.getOutputStream());
		 //draw line on the server GUI
		 parseServerInput(s);
		 //Echo the result to client using the created output stream object
		 outputStream.println(s);
		 outputStream.flush();
		}
		} catch (IOException e) {
			System.out.println("Unable to write to client's stream ..");
		}
	  System.out.println("A client is disconnected..."+"\n");	
	}
	
	public void parseServerInput(String input) {

		String[] s = null;
		if(input.split("-").length >1 ) {
		s = input.split("-");
		lineComponent.drawLine(s[0],s[1]);
		}
		else {
			System.out.println(input);
		}
		
	}
	
}
