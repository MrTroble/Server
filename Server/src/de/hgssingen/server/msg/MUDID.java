package de.hgssingen.server.msg;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import de.hgssingen.server.MainServer;
import de.hgssingen.server.command.Rolles;
import de.hgssingen.server.util.Util;

public class MUDID extends Message{

	public static final HashMap<String, Socket> UDID_SOCKET = new HashMap<>();
	public static final HashMap<Socket, String> SOCKET_UDID = new HashMap<>();
		
	@Override
	public void toByte(ArrayList<Byte> list) {}

	@Override
	public void fromByte(ArrayList<Byte> list, Socket sk) {
		String str = new String(Util.toArray(list));
		if(str.startsWith("UDID:")){
			String[] sg = str.replaceFirst("UDID:", "").split(MainServer.SEPERATOR);
			for(String s : sg){
				MainServer.debug.write(s);
			}
			UDID_SOCKET.put(sg[0], sk);
			SOCKET_UDID.put(sk, sg[0]);
			MainServer.DATABASE.addValueIfNotExists("uuid", sg[0], new String[] {sg[1],Rolles.ALL.toString()});
			MainServer.debug.write("Added " + sk + " with UDID: " + sg[0] + " and Name " + sg[1]);
		}
	}

	@Override
	public boolean isReader() {
		return true;
	}

	@Override
	public boolean isWriter() {
		return false;
	}

	@Override
	public boolean notNeedsAuth() {
		return true;
	}
	
	
}
