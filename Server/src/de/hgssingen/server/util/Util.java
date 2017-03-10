package de.hgssingen.server.util;

import java.util.*;

public class Util {

	public static byte[] toArray(ArrayList<Byte> bt) {
		byte[] bts = new byte[bt.size()];
		int i = 0;
		for(Byte t : bt){
				bts[i] = t;
				i++;
		}
		return bts;
	}
	
	public static void addToArray(ArrayList<Byte> li,byte[] arg){
		for(byte b : arg){
			li.add(b);
		}
	}
	
}
