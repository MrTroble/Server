package de.hgssingen.server.msg;

import java.util.ArrayList;

public interface IMessage {

	public void toByte(ArrayList<Byte> list);
	
	public void fromByte(ArrayList<Byte> list);
	
	public boolean isReader();
	
	public boolean isWriter();
	
}
