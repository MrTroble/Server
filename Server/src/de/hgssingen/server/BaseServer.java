package de.hgssingen.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.msg.Message;

public class BaseServer extends ServerSocket{
	
	private ArrayList<Socket> skt = new ArrayList<>();
	private MainServer server;
	
	public BaseServer(int i) throws IOException {
		super(i);
		this.server = MainServer.getInstance();
		blockAndAccept();
	}

	private void blockAndAccept() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ArrayList<Byte> btr = new ArrayList<>();
					Socket sk = accept();
					skt.add(sk);
					InputStream str = sk.getInputStream();
					int i = 0;
				    while((i = str.read()) >= 0){
				    	if(i == Byte.MAX_VALUE){
				    		readMessage(btr,sk);
				    		btr.clear();
				    	}else{
					    	btr.add((byte) i);
				    	}
				    }
				} catch (Throwable e) {
					server.err.println("An Erroring Socket while Init");
				}
			}
		}).start();
	}	
	
	public void sendMessageTo(Socket sk,Message msg){
		if(!msg.isWriter())return;
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
	
	public void readMessage(ArrayList<Byte> bts,Socket s){
		for(Message msg : Message.reader){
			msg.fromByte(bts,s);
		}
	}
	
}
