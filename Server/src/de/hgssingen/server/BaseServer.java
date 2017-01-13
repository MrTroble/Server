package de.hgssingen.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.msg.Message;

public class BaseServer extends ServerSocket{
	
	private ArrayList<Socket> skt = new ArrayList<>();
	
	public BaseServer(int i) throws IOException {
		super(i);
		blockAndAccept();
	}
	//TODO Lock:
	//https://developer.apple.com/library/prerelease/content/qa/qa1652/_index.html#//apple_ref/doc/uid/DTS40008977

	private void blockAndAccept() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ArrayList<Byte> btr = new ArrayList<>();
					Socket sk = accept();
					skt.add(sk);
					MainServer.log.write(sk + " connected to Server");
					InputStream str = sk.getInputStream();
					int i = 0;
					byte[] lastBytes = new byte[6];
					
				    while((i = str.read()) >= 0){
				    	if(btr.size() > 6){
						    for(int x = 5;x > 0;x--){
						    	lastBytes[5 - x] = btr.get(btr.size() - x); 
						    }
				        }
				    	if(new String(lastBytes).equals("endpak")){
				    		readMessage(btr,sk);
				    		btr.clear();
				    	}else{
				    		MainServer.debug.write(String.valueOf(i));
					    	btr.add((byte) i);
				    	}
				    }
				    str.close();
				} catch (Throwable e) {
					MainServer.err.write("An Erroring Socket while Init");
					MainServer.err.writeTrace(e);
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
		} catch (Throwable e) {
			MainServer.err.write("Erroring Socket");
			MainServer.err.writeTrace(e);
		}
	}
	
	public void readMessage(ArrayList<Byte> bts,Socket s){
		for(Message msg : Message.reader){
			if(msg.isReader())
			msg.fromByte(bts,s);
		}
	}
	
}
