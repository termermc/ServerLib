package net.termer.serverlib;

import java.net.Socket;

/**
 * The basic server listener object to be extended.
 * @author _termer_
 *
 */
public abstract class ServerListener {
	/**
	 * The method that is called when an ip connects to the server
	 * @param ip The ip that connected
	 */
	public void onConnect(Socket ip) {
		
	}
	/**
	 * The method that is called when an ip disconnects from the server
	 * @param ip The ip that disconnected
	 */
	public void onDisconnect(Socket ip) {
		
	}
	/**
	 * The method that is called when an ip sends a string to the server
	 * @param sender The ip that sent the string
	 * @param data The string sent
	 */
	public void onStringReceive(Socket sender, String data) {
		
	}
	/**
	 * The method that is called when an ip sends an integer to the server
	 * @param sender The ip that sent the integer
	 * @param data The integer sent
	 */
	public void onIntReceive(Socket sender, int data) {
		
	}
}
