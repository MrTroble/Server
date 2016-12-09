package de.hgssingen.server.msg;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

import de.hgssingen.server.MainServer;
import de.hgssingen.server.util.Util;

public class MGetFiles extends Message{
	
	public MGetFiles() {
		super();
	}

	@Override
	public void toByte(ArrayList<Byte> list) {
		File fl = new File(System.getProperty("user.dir") + File.separator + "/Files/");
		fl.mkdir();
		for(String s : fl.list()){
			Util.addToArray(list, (s + "\n").getBytes());
		}
		list.add(Byte.MAX_VALUE);
	}

	@Override
	public void fromByte(ArrayList<Byte> list,Socket sk) {
		if(new String(Util.toArray(list)).equals("request:files")){
			MainServer.getServer().sendMessageTo(sk, this);
		}
	}

	@Override
	public boolean isReader() {
		return true;
	}

	@Override
	public boolean isWriter() {
		return true;
	}

}
