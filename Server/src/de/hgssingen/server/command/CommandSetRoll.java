package de.hgssingen.server.command;

import de.hgssingen.server.MainServer;

public class CommandSetRoll extends Command{

	public CommandSetRoll() {
		super("roll", "(MAC-Adress) (roll) - Gives a roll to a member");
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length == 0){
			MainServer.log.write("Rolles:");
			for(Rolles r : Rolles.values()){
				MainServer.log.write(r.toString());
			}
			return;
		}
		if(args.length == 2){
			MainServer.debug.write("Saved roll " + args[0] + " for MAC-Adress " + args[1]);
		    return;
		}
		MainServer.err.write("Wrong Arguments (" + args.length + ") 0 or 2 are requiered!");
	}

}
