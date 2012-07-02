package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird 
	public boolean connected = false;	// Public field to check if a client connected
	private Socket client = null;
	private PrintWriter clientWriter;
	private BufferedReader clientReader;
	private Thread t;
	
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
			
			// And for rest of the program, handle messages
			String inputLine, outputLine;
			while( (inputLine = this.clientReader.readLine()) != null )
			{
				// Close upon "bye"
				if( inputLine.equals("bye") )
					break;
				processMsg(inputLine);
			}
			
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
			return new BufferedReader(new InputStreamReader(this.client.getInputStream()));
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
		System.out.println("Client said: " + message);
	}
}

