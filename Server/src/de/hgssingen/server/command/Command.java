package de.hgssingen.server.command;

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
		if(this.name.equals(name)){
			this.onCommand(args);
		}
	}
	
	public abstract void onCommand(String[] args);

}
