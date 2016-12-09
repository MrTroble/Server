package de.hgssingen.server.command;

import de.hgssingen.server.MainServer;

public class CommandSetRoll extends Command{

	public CommandSetRoll() {
		super("roll", "/roll MAC-Adress roll - Gives a roll to a member");
	}

	@Override
	public void onCommand(String[] args) {
		if(args == null)throw new NullPointerException("Command execution failed please try again");
		if(args.length == 0){
			MainServer.log.print("Rolles:");
			for(Rolles r : Rolles.values()){
				MainServer.log.print(r.toString());
			}
			return;
		}
		if(args.length == 2){
			MainServer.debug.print("Saved roll " + args[0] + " for MAC-Adress " + args[1]);
		    return;
		}
		MainServer.log.println("Wrong Arguments (" + args.length + ") 0 or 2 are requiered");
	}

}
