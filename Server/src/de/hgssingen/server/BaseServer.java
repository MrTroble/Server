package de.hgssingen.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.msg.IMessage;

public class BaseServer extends ServerSocket{
	
	private ArrayList<Socket> skt = new ArrayList<>();
	private final MainServer server;
	
	public BaseServer(int i, MainServer mainServer) throws IOException {
		super(i);
		this.server = mainServer;
		blockAndAccept();
	}

	private void blockAndAccept() {
		try {
			Socket sk = this.accept();
			skt.add(sk);
		} catch (Throwable e) {
			server.err.println("An Erroring Socket while Init");
		}
	}	
	
	public void sendMessageTo(Socket sk,IMessage msg){
		ArrayList<Byte> bt = new ArrayList<>();
		msg.toByte(bt);
		byte[] args = new byte[bt.size()];
		int i = 0;
		for(byte b : bt){
			args[i] = b;
			i++;
		}
		try {
			sk.getOutputStream().write(args);
		} catch (Exception e) {
			server.err.println("Excepting Socket");
		}
	}
	
	
	
}
