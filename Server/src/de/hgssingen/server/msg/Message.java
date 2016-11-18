package de.hgssingen.server.msg;

import java.util.ArrayList;

public abstract class Message {
	
	public static final ArrayList<Message> reader = new ArrayList<>();
	
	public Message() {
		if(this.isReader())
		reader.add(this);
	}

	public abstract void toByte(ArrayList<Byte> list);
	
	public abstract void fromByte(ArrayList<Byte> list);
	
	public abstract boolean isReader();
	
	public abstract boolean isWriter();
	
}
