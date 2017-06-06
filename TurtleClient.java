/*
* 
* File Name:  TurtleClient.java
* Author1: Harika Hari (NAU ID: hh453@nau.edu)
* Author2: Caitlin Barrett (NAU ID: cb2693@nau.edu)
*  
* Description: THis is a GUI based java code that connects with a Server to serve the user needs. 
* It interacts with the server by sending the user input to the server.
* THe GUI client is intelligent enough to handle the errors and update user appropriately with messages.
* The functionality of the TurtleClient.java is to accept the input from the user 
* and process it and send it to the server for executing an appropriate action sequence based on the user's input.
* 
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;



public class TurtleClient extends JFrame implements ActionListener {

	//member variables required for Turtle Client GUI
	private JLabel label, labelInstruction;
	private JTextField tfServer, tfPort, tfLength;
	private JButton connectToServer, logout, liftPen, putPen, moveUp, moveDown, moveLeft, moveRight;
	private boolean connected;
	private Socket socket;
	private int length = 0;

	// Constructor connection receiving a socket number
	TurtleClient(String host, int port) {
		//creating panel on the top to allow user to enter IP address and the port details
		super("TURTLE CLIENT");
		JPanel northPanel = new JPanel(new GridLayout(3,1));
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		tfServer = new JTextField(host);
		tfPort = new JTextField("" + port);
		tfPort.setHorizontalAlignment(SwingConstants.CENTER);
		tfLength = new JTextField("" + 15);
		tfLength.setHorizontalAlignment(SwingConstants.RIGHT);

		//add server and port details to north panel 
		serverAndPort.add(new JLabel("Host/IP :  "));
		serverAndPort.add(tfServer);
		serverAndPort.add(new JLabel("Port :  "));
		serverAndPort.add(tfPort);
		serverAndPort.add(new JLabel("Length :  "));
		serverAndPort.add(tfLength);
		northPanel.add(serverAndPort);
		
		label = new JLabel("Please Click on ConnectToServer to start using the White Board", SwingConstants.LEFT);
		northPanel.add(label);
		
		//add connection button to the north panel
		connectToServer = new JButton("ConnectToServer");
		connectToServer.addActionListener(this);
		connectToServer.setBounds(100,150,80,30);  
		northPanel.add(connectToServer);
		add(northPanel, BorderLayout.NORTH);

		// The CenterPanel which is actual action window with user label and the buttons
		JPanel centerPanel = new JPanel();
		
		labelInstruction = new JLabel("<html> Please Follow below Instructions To start drawing on the white board <br >" +
				"<br> Hit Put Pen to activate white board and to start drawing " +
				"<br> Hit Move Up to draw line in upward direction " +
				"<br> Hit Move Down to draw line in downard direction" +
				"<br> Hit Move left to draw line in left direction  " +
				"<br> Hit Move right to draw line in right direction"+
			    "<br> Hit Lift Pen to deactivate white board and stop drawing <br ></html>", SwingConstants.LEFT);
		//start of user action buttons
		liftPen = new JButton("Lift Pen");
		liftPen.addActionListener(this);
		liftPen.setEnabled(false);
		
		putPen = new JButton("Put Pen");
		putPen.addActionListener(this);
		putPen.setEnabled(false);
		
		moveUp = new JButton("Move Up");
		moveUp.addActionListener(this);
		moveUp.setEnabled(false);
		
		moveDown = new JButton("Move Down");
		moveDown.addActionListener(this);
		moveDown.setEnabled(false);
		
		moveLeft = new JButton("Move Left");
		moveLeft.addActionListener(this);
		moveLeft.setEnabled(false);
		
		moveRight = new JButton("Move Right");
		moveRight.addActionListener(this);
		moveRight.setEnabled(false);
		//end of user action buttons 
		
		//adding user action buttons to the panel.
		centerPanel.add(labelInstruction);
		centerPanel.add(liftPen);
		centerPanel.add(putPen);
		centerPanel.add(moveUp);
		centerPanel.add(moveDown);
		centerPanel.add(moveLeft);
		centerPanel.add(moveRight);
		
		add(centerPanel, BorderLayout.CENTER);

		//creating the logout button to logout the server client connection
		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setEnabled(false);		

		JPanel southPanel = new JPanel();
		southPanel.add(logout);
		add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(450, 450);
		setVisible(true);

	}

	void append(String str) {
		//this will enable a error message if the connection is disconnected
		JOptionPane.showMessageDialog(this,"Unable to Contact Server ...");  
	}
		
	void sendMessage(String msg) {
			
			// try Connecting to a server
			if(connected) {
			// try Sending message to a server
			try {
				PrintStream msgToServer = new PrintStream(socket.getOutputStream());
				msgToServer.println(msg);
				
				 BufferedReader messageFromServer = 
		            		new BufferedReader(new InputStreamReader((socket.getInputStream())));
				String serverMessage = messageFromServer.readLine();
				if(msg.equals("logout"))
					socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				append("\nFailed to Send/Receive Message to/from the Server");
			}
			}
		}
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if it is the Logout button disconnect the connection and disable everything
		if(o == logout) {
			sendMessage("logout");
			connected = false;
			connectToServer.setEnabled(true);
			tfServer.setEditable(true);
			tfPort.setEditable(true);
			tfLength.setEditable(true);
			liftPen.setEnabled(false);
			putPen.setEnabled(false);
			moveUp.setEnabled(false);
			moveDown.setEnabled(false);
			moveLeft.setEnabled(false);
			moveRight.setEnabled(false);
			logout.setEnabled(false);
			
			label.setText("Please Click on ConnectToServer to start using the White Board");
			return;
		}
		//if it is the lift pen button disable all user action buttons
		if(o == liftPen) {
			sendMessage("lift");
			liftPen.setEnabled(false);
			putPen.setEnabled(true);
			moveUp.setEnabled(false);
			moveDown.setEnabled(false);
			moveLeft.setEnabled(false);
			moveRight.setEnabled(false);
			return;
		}
		
		//if it is the put pen button enable user action buttons
		if(o == putPen) {
			sendMessage("put");
			liftPen.setEnabled(true);
			putPen.setEnabled(false);
			moveUp.setEnabled(true);
			moveDown.setEnabled(true);
			moveLeft.setEnabled(true);
			moveRight.setEnabled(true);
			return;
		}
		
		if(o == moveUp) {
			sendMessage("up -"+length);
			return;
		}
		if(o == moveDown) {
			sendMessage("down -"+length);
			return;
		}
		if(o == moveLeft) {
			sendMessage("left -"+length);
			return;
		}
		if(o == moveRight) {
			sendMessage("right -"+length);
			return;
		}

		if(o == connectToServer) {
			// empty serverAddress ignore it
			String server = tfServer.getText().trim();
			if(server.length() == 0)
				return;
			// empty or invalid port number, ignore it
			String portNumber = tfPort.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   // nothing I can do if port number is not valid
			}
			//empty or invalid length value
			String len = tfLength.getText().trim();
			if(len.length() == 0)
				return;

			// try creating a new Client with GUI
			connected = connectToServer(server, port);
			
			if(!connected)
				return;
			label.setText("Connected to Server  ---- Start using White Board");

			// disable connectToServer button
			connectToServer.setEnabled(false);
			
			try {
				length = Integer.parseInt(len);
			}
			catch(Exception en) {
				return;   // nothing I can do if length is not valid
			}
			
			logout.setEnabled(true);
			putPen.setEnabled(true);
			
			// disable the Server and Port JTextField
			tfServer.setEditable(false);
			tfPort.setEditable(false);
			tfLength.setEditable(false);
			// Action listener for when the user enter a message
		}

	}
	
	
	boolean connectToServer(String server, int port) {
			
		try {
			//establishing the connection to the server
			socket = new Socket(server,port);
			return true;
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			append("\nFailed to Connect to server");
			return false;
		}
	}

	public static void main(String[] args) {
		new TurtleClient("127.0.0.1",23657);
	}

}
