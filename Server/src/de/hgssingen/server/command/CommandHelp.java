package de.hgssingen.server.command;

import de.hgssingen.server.*;

public class CommandHelp extends Command{

	public CommandHelp() {
		super("help", "<Command> - Shows the commands or help to one");
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length == 0){
			for(Command c : MainServer.cmds)MainServer.log.write(c.getName());
			return;
		}
		if(args.length == 1){
			for(Command c : MainServer.cmds){
				if(c.getName().equals(args[0])){
					MainServer.log.write("/" + c.getName() + " " + c.getDescription());
					return;
				}
			}
			MainServer.err.write("Command dosen't exits!");
			return;
		}
		MainServer.err.write("Wrong Arguments (" + args.length + ") 0 or 1 are requiered!");
	}

}
