package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Alex.Gameplay;

public class Client implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird
	private final String host = "localhost"; 
	public boolean connected = false;	// Public field to check if a client connected
	private Socket socket;
	private PrintWriter socketWriter;
	private BufferedReader socketReader;
	private Gameplay gameplay;
	private Thread t;
	
	/**
	 * Constructor
	 * 
	 * Start a thread.
	 *  
	 */
	public Client()
	{
		this.t = new Thread(this);
		this.t.start();
	}
	
	/**
	 * Assign a gameplay to this server
	 * 
	 * @param	Gameplay	gameplay
	 */
	public void setGameplay( Gameplay gameplay)
	{
		this.gameplay = gameplay;
	}
	
	/**
	 * run
	 * 
	 * Create a socket connect to server
	 */
	public void run()
	{
		try
		{
			this.socket = new Socket(this.host, this.port);
			// Wait for client to connect
			System.out.println("Conencted to: " + socket.getInetAddress());
			this.connected = true;
			
			this.socketReader = makeReader();
			this.socketWriter = makeWriter();
			// And for rest of the program, handle messages
			String inputLine;
			while( (inputLine = this.socketReader.readLine()) != null )
			//while(true)
			{
				//inputLine = this.socketReader.readLine();
				// Close upon "bye"
				//System.out.println("Got this message from the server: " + inputLine);
				if( inputLine.equals("bye") )
					break;
				processMsg(inputLine);
			}
			//System.out.println("Shutting down client");
			// Shutdown everything;
			this.socketWriter.close();
			this.socketReader.close();
			this.socket.close();
			socket.close();
			this.t.interrupt();		
		}
		catch( IOException ioE )
		{
			System.out.println(ioE.getMessage());
		}
	}
	
	/**
	 * makeWriter
	 * 
	 * Create a writer on the socket for communicating with the client
	 * 
	 */
	private PrintWriter makeWriter()
	{
		try {
			return new PrintWriter(this.socket.getOutputStream(), true );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * makeReader
	 * 
	 * Creater a reader on the socket to receive messages from the client
	 * 
	 */
	private BufferedReader makeReader()
	{
		try {
			return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * processMsg
	 * 
	 * Upon an incoming Message from the client, this method is called, given the message as a String
	 * 
	 * @param	String	message
	 *
	 */
	private void processMsg( String message )
	{
		String[] msg = message.split("\\s+");
		//System.out.println("Server said: " + msg[0]);
		
		// There may be other messages than just movements, so filter them
		if( msg[0].equals("won") )
			this.gameplay.gameWon( Integer.parseInt(msg[1]) );
		else
			this.gameplay.keyCheck(msg[0], msg[1]);
	}
	
	/**
	 * sendMsg
	 * 
	 * Send a Message through the socket
	 * 
	 * @param	String	message
	 * @param	int		playerId
	 * 
	 */
	public void sendMessage( String message, int playerId )
	{
		//System.out.println("Sending message: " + message + " " + playerId);
		this.socketWriter.println(message + " " + playerId);
	}
}