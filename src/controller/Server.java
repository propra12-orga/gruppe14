package controller;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	/**
	 * Constructor
	 * 
	 * Creates a socket and waits for a client to connect.
	 * 
	 * @throws IOException 
	 */
	public Server() throws IOException
	{
		ServerSocket socket = new ServerSocket(11111);
		// Wait for client to connect
		System.out.println("Bound to: " + socket.getInetAddress());
		System.out.println("Waiting for client to connect...");
		socket.accept();
	}
}
