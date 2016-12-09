package de.hgssingen.server.msg;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.util.Util;

public class MFile extends Message{

	private String name;
	
	public MFile() {}
	
	public MFile(String file_name) {this.name = file_name;}

	@Override
	public void toByte(ArrayList<Byte> list) {
		try{
		File fl = new File(System.getProperty("user.dir") + File.separator + "/Files/" + this.name);
		FileInputStream str = new FileInputStream(fl);
		while(str.available() > 0){
			list.add((byte) str.read());
		}
		str.close();
		list.add(Byte.MAX_VALUE);
		}catch(Throwable t){
			sv.err.println("Error While Writing a File to Socket " + this.name);
			t.printStackTrace(sv.err);
		}
	}

	@Override
	public void fromByte(ArrayList<Byte> list, Socket sk) {
		try{
		String str = new String(Util.toArray(list));
		if(str.startsWith("requestfiles:")){
			String st = str.split(":")[1]; 
			sv.getServer().sendMessageTo(sk, new MFile(st));
		}
		}catch(Throwable t){
			sv.err.println("Error While Reading a Message(File Request)");
			t.printStackTrace(sv.err);
		}
	}

	@Override
	public boolean isReader() {
		return true;
	}

	@Override
	public boolean isWriter() {
		return this.name != null;
	}

}
