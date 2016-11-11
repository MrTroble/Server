package de.hgssingen.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.msg.IMessage;

public class BaseServer extends ServerSocket{
	
	private ArrayList<Socket> skt = new ArrayList<>();
	
	public BaseServer(int i) throws IOException {
		super(i);
		blockAndAccept();
	}

	private void blockAndAccept() {
		try {
			Socket sk = this.accept();
			skt.add(sk);
		} catch (Throwable e) {
			System.err.println("An Erroring Socket while Init");
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
			System.err.println("Excepting Socket");
		}
	}
	
}
