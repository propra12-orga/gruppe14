package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Alex.Gameplay;

public class Server implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird 
	public boolean connected = false;	// Public field to check if a client connected
	private Socket client = null;
	private PrintWriter clientWriter;
	private BufferedReader clientReader;
	private Thread t;
	private Gameplay gameplay; 
	
	/**
	 * Constructor
	 * 
	 * Start a thread.
	 *  
	 */
	public Server()
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
	 * Create a socket and wait for client to connect
	 */
	public void run()
	{
		try
		{
			ServerSocket socket = new ServerSocket(this.port);
			// Wait for client to connect
			System.out.println("Bound to: " + socket.getLocalSocketAddress() + ":" + socket.getLocalPort());
			System.out.println("Waiting for client to connect...");
			this.client = socket.accept();
			this.connected = true;
			
			// Initiate reader and writer
			this.clientWriter = makeWriter();
			this.clientReader = makeReader();

			//System.out.println("Server is listening");
			
			// And for rest of the program, handle messages
			String inputLine;
			while( (inputLine = this.clientReader.readLine()) != null )
			//while(true)
			{
				//inputLine = this.clientReader.readLine();
				// Close upon "bye"
				//System.out.println("Got this message from the client: " + inputLine);
				if( inputLine.equals("bye") )
					break;
				processMsg(inputLine);
			}
			//System.out.println("Shutting down server");
			// Shutdown everything;
			this.clientWriter.close();
			this.clientReader.close();
			this.client.close();
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
			return new PrintWriter(this.client.getOutputStream(), true );
		} catch (IOException e) {
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
			return new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * processMsg
	 * 
	 * Upon an incoming Message from the client, this method is called, given the message as a String.
	 * All messages are directly forwarded to the gameplay.
	 * 
	 * @param	String	message
	 *
	 */
	private void processMsg( String message )
	{
		String[] msg = message.split("\\s+");
		//System.out.println("Client said: " + msg[0]);
		this.gameplay.keyCheck(msg[0],msg[1]);
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
		this.clientWriter.println(message + " " + playerId);
	}
}

