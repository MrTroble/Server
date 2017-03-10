package de.hgssingen.server.event;

import de.hgssingen.server.*;

public class EventHolder {

	@SEvent
	public void onMD5Change(EventMD5Changed changed){
		MainServer.debug.write("MD5 changed");
//		MainServer.getServer().senMessage();
	}
	
}
