package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird
	private final String host = "localhost"; 
	public boolean connected = false;	// Public field to check if a client connected
	private Socket socket;
	private PrintWriter socketWriter;
	private BufferedReader socketReader;
	
	/**
	 * Constructor
	 * 
	 * Start a thread.
	 *  
	 */
	public Client()
	{
		Thread t = new Thread(this);
		t.start();
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
			
			this.socketWriter.println("Message 1");
			this.socketWriter.println("Message 2");
			this.socketWriter.println("Message 3");
			this.socketWriter.println("Message 4");
			
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
		System.out.println("Client said: " + message);
	}
}