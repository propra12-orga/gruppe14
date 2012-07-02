package controller;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird 
	public boolean connected = false;	// Public field to check if a client connected
	
	/**
	 * Constructor
	 * 
	 * Start a thread.
	 *  
	 */
	public Server()
	{
		Thread t = new Thread(this);
		t.start();
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
			System.out.println("Bound to: " + socket.getInetAddress());
			System.out.println("Waiting for client to connect...");
			socket.accept();
			this.connected = true;
		}
		catch( IOException ioE )
		{
			System.out.println(ioE.getMessage());
		}
	}
}
