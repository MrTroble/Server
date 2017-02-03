package de.hgssingen.server.msg;

import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.command.Rolles;

public abstract class Message {
	
	public static final ArrayList<Message> reader = new ArrayList<>();

	public abstract void toByte(ArrayList<Byte> list);
	
	public abstract void fromByte(ArrayList<Byte> list,Socket sk);
	
	public abstract boolean isReader();
	
	public abstract boolean isWriter();
	
    static {
		reader.add(new MGetFiles());
		reader.add(new MFile());
		reader.add(new MUDID());
	}
	
    public boolean notNeedsAuth(){
		return false;
    }
    
    public Rolles getRequiredRoll(){
    	return Rolles.ALL;
    }
}
