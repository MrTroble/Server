package de.hgssingen.server.util;

import java.util.*;

public class Transformer<T> {

	private final ArrayList<T> list;
	
	public Transformer(ArrayList<T> list) {
		this.list = list;
	}
	
	@SuppressWarnings("unchecked")
	public T[] transform(){
		return (T[]) this.list.toArray(); 
	}
	
}
