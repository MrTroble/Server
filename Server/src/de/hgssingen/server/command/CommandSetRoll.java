package de.hgssingen.server.command;

import org.json.*;

import de.hgssingen.server.*;

public class CommandSetRoll extends Command{

	public CommandSetRoll() {
		super("roll", "(roll) (UUID)- Gives a roll to a member");
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
		try{
		if(args.length == 2 && Enum.valueOf(Rolles.class, args[0]) != null){
			JSONArray value;
			if((value = (JSONArray) MainServer.DATABASE.getKeyFromDatabase("uuid", args[1])) != null){
				MainServer.DATABASE.value("uuid", args[1], new Object[]{value.get(0),args[0]});
				MainServer.log.write("Saved roll " + args[0] + " for UUID " + args[1]);
			}else{
				MainServer.log.write("UUID not found in DB");
			}
			return;
		}
		}catch(IllegalArgumentException ex){
			MainServer.err.write("Not a vailed roll (IllegalArgumentException: CommandSetRoll.class - 26)");
			return;
		}catch(ClassCastException ex){
			MainServer.err.writeTrace(ex);
			MainServer.err.write("Reloading JSON!");
			MainServer.DATABASE.reload("uuid");
			MainServer.err.write("Reloaded! You can try again now.");
		}
		MainServer.err.write("Wrong Arguments (" + args.length + ") 0 or 2 are requiered!");
	}

}
