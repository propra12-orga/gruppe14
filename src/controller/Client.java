package controller;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird
	private final String host = "localhost"; 
	public boolean connected = false;	// Public field to check if a client connected
	
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
			Socket socket = new Socket(this.host, this.port);
			// Wait for client to connect
			System.out.println("Conencted to: " + socket.getInetAddress());
			this.connected = true;
		}
		catch( IOException ioE )
		{
			System.out.println(ioE.getMessage());
		}
	}
}