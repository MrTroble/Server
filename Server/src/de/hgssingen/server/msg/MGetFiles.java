package de.hgssingen.server.msg;

import java.io.*;
import java.net.*;
import java.util.*;

import de.hgssingen.server.util.*;

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
	}

	@Override
	public void fromByte(ArrayList<Byte> list,Socket sk) {
		String str = new String(Util.toArray(list));
		if(str.startsWith("request-files")){
//			String[] File_SH = str.replaceFirst("request-files", "").split(",");
//			for(String s : File_SH){
//				String[] fl_sh_send = s.split(":");
//			}
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
