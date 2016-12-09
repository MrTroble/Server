package de.hgssingen.server.command;

import de.hgssingen.server.MainServer;

public abstract class Command {
	
	private final String name,desc; 
	
	public Command(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}
	
	public void execute(String name,String[] args){
		try{
		if(this.name.equals(name)){
			this.onCommand(args);
		}
		}catch(Throwable t){
			MainServer.err.printTrace(t);
		}
	}
	
	public abstract void onCommand(String[] args);

}
