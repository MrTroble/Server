package de.hgssingen.server.event;

import java.lang.reflect.*;
import java.util.*;

import de.hgssingen.server.*;

public class EventSystem {

	public static EventSystem INSTANCE = new EventSystem();
	
	private ArrayList<Object> toexec = new ArrayList<>();
	
	public EventSystem() {
		
	} 
	
	public void fire(Event evt){
		if (evt != null) {
			for(Object o : toexec){
				for(Method md : o.getClass().getMethods()){
					if(md.getAnnotation(SEvent.class) != null){
						try {
							md.invoke(o, evt);
						} catch (Exception e) {
							MainServer.err.writeTrace(e);
						}
					}
				}
			}
		}
	}
	
	public void bind(Object obj){
		this.toexec.add(obj);
	}
}
