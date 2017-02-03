package de.hgssingen.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import de.hgssingen.server.msg.MUDID;
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
		while(true){
		Socket sk;
		try {
			sk = accept();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String s = "";
					skt.add(sk);
					MainServer.log.write(sk + " connected to server");
					InputStream str = sk.getInputStream();
					Scanner sc = new Scanner(str);
				    while(sc.hasNext()){
				    	s += sc.next();
				    	System.out.println(s);
			    		MainServer.debug.write(s);
				    	if(s.endsWith("EndConnectedMessage")){
				    		ArrayList<Byte> bts = new ArrayList<Byte>();
				    		MainServer.debug.write("true");
				    		String sdr = s.replace("EndConnectedMessage", "");
				    		byte[] btds = sdr.getBytes();
				    		for(byte b : btds){
				    			bts.add(b);
				    		}
				    		readMessage(bts, sk);
				    		s = "";
				    	}
				    }
				    sc.close();
				    MainServer.log.write(sk.toString() + " disconnected");
				    str.close();
				} catch (Throwable e) {
					MainServer.err.write("An erroring socket while init");
					MainServer.err.writeTrace(e);
				}
			}
		}).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	  }
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
			MainServer.err.write("Erroring socket");
			MainServer.err.writeTrace(e);
		}
	}
	
	public void readMessage(ArrayList<Byte> bts,Socket s){
		for(Message msg : Message.reader){
			if(msg.isReader() && (MUDID.SOCKET_UDID.get(s) != null || msg.notNeedsAuth()))
			msg.fromByte(bts,s);
		}
	}
	
}
