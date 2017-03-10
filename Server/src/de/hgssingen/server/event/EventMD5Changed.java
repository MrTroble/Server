package de.hgssingen.server.event;

import java.io.*;

public class EventMD5Changed extends Event{

	private final File fl;
	
	public EventMD5Changed(File fl) {
		this.fl = fl;
	}
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
	public File getFile(){
		return fl;
	}
}
