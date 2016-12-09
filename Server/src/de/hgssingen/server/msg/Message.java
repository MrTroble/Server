package de.hgssingen.server.msg;

import java.net.Socket;
import java.util.ArrayList;

public abstract class Message {
	
	public static final ArrayList<Message> reader = new ArrayList<>();

	public abstract void toByte(ArrayList<Byte> list);
	
	public abstract void fromByte(ArrayList<Byte> list,Socket sk);
	
	public abstract boolean isReader();
	
	public abstract boolean isWriter();
	
	public static void init(){
		reader.add(new MGetFiles());
		reader.add(new MFile());
	}
	
}
