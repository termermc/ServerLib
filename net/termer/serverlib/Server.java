package net.termer.serverlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * The basis of the whole library, this class should be instantiated using the address of the machine it's being run on and the port, or just a port to run it locally.
 * @author _termer_
 *
 */
public class Server {
	private ArrayList<ServerListener> listeners = new ArrayList<ServerListener>();
	private ServerSocket s = null;
	
	/**
	 * Creates a new server running on the provided address and port. Make sure the address is set to the machine's current public ip.
	 * @param address The address to run the server on
	 * @param port The port to run the server on
	 * @throws UnknownHostException If the provided address is unknown
	 * @throws IOException If there is a problem setting up the server
	 */
	public Server(String address, int port) throws UnknownHostException, IOException {
		s = new ServerSocket(port,50,InetAddress.getByName(address));
	}
	/**
	 * Creates a new server running locally on the provided port.
	 * @param port The port to run the server on
	 * @throws IOException If there is a problem setting up the server
	 */
	public Server(int port) throws IOException {
		s = new ServerSocket(port);
	}
	
	/**
	 * Starts the server.
	 * @throws IOException If there is a problem starting the server
	 */
	public void start() throws IOException {
		while(true) {
			new SocketHandler(s.accept(),this).start();
			new SocketStringHandler(s.accept(),this).start();
		}
	}
	
	/**
	 * Gets the ServerSocket associated with this server.
	 * @return The ServerSocket associated with this server
	 */
	public ServerSocket getServerSocket() {
		return s;
	}
	/**
	 * Gets all active ServerListener objects associated with this particular server.
	 * @return All active ServerListeners
	 */
	public ServerListener[] getListeners() {
		return listeners.toArray(new ServerListener[0]);
	}
	
	/**
	 * Registers a listener to be used by this server.
	 * @param listener The listener to register
	 */
	public void addListener(ServerListener listener) {
		listeners.add(listener);
	}
	/**
	 * Removes a listener from this server.
	 * @param listener The listener to remove
	 */
	public void removeListener(ServerListener listener) {
		listeners.remove(listener);
	}
	/**
	 * Removes all active listeners from this server
	 */
	public void removeListeners() {
		listeners.clear();
	}
	
	private class SocketStringHandler extends Thread {
		private Socket s = null;
		private Server sv = null;
		
		public SocketStringHandler(Socket socket, Server server) {
			s = socket;
			sv = server;
		}
		public void start() {
			try {
				while(true) {
					BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
					for(ServerListener sl : sv.getListeners()) {
						sl.onStringReceive(s, r.readLine());
					}
				}
			} catch(IOException e) {
				
			}
		}
	}
	private class SocketHandler extends Thread {
		private Socket s = null;
		private Server sv = null;
		
		public SocketHandler(Socket socket, Server server) {
			s = socket;
			sv = server;
		}
		public void start() {
			for(ServerListener sl : sv.getListeners()) {
				sl.onConnect(s);
			}
			try {
				while(true) {
					for(ServerListener sl : sv.getListeners()) {
						sl.onIntReceive(s, s.getInputStream().read());
					}
				}
			} catch(IOException e) {
				for(ServerListener sl : sv.getListeners()) {
					sl.onDisconnect(s);
				}
			}
		}
	}
}
