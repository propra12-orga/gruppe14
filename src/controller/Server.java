package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	/* Instanzvariablen */
	private final int port = 11111;		// Port, der zum Verbinden geöffnet wird 
	public boolean connected = false;	// Public field to check if a client connected
	private Socket client = null;
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
			System.out.println("Bound to: " + socket.getLocalSocketAddress() + ":" + socket.getLocalPort());
			System.out.println("Waiting for client to connect...");
			this.client = socket.accept();
			this.connected = true;
		}
		catch( IOException ioE )
		{
			System.out.println(ioE.getMessage());
		}
	}
}
