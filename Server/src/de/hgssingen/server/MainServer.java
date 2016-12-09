package de.hgssingen.server;

import java.util.ArrayList;
import java.util.Scanner;

import de.hgssingen.server.command.Command;
import de.hgssingen.server.command.CommandSetRoll;
import de.hgssingen.server.log.CommonLogger;

public class MainServer {

	private static BaseServer SERVER_INSTANCE; 
	public static final CommonLogger log = new CommonLogger(System.out, "INFO");
	public static final CommonLogger err = new CommonLogger(System.err, "ERROR");
	public static final CommonLogger debug = new CommonLogger(System.out, "DEBUG");
	public static final ArrayList<Command> cmds = new ArrayList<>();

	public static void startServer(int i){
		
		cmds.add(new CommandSetRoll());
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				log.println("Server Beginn Loading");
				try{
				SERVER_INSTANCE = new BaseServer(i);
				}catch(Throwable t){
					err.println("Server Failed Loading");
					err.printTrace(t);
				}
				log.println("End Loading");
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.gc();
					try {
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						log.printTrace(e);
					}
				}
			}
		}).start();
		
		awaitAdminInput();
	}
	
	private static void awaitAdminInput() {
		while(true){
			Scanner in = new Scanner(System.in);
			if(in.hasNextLine()){
				String n = in.nextLine();
				if(n.startsWith("/")){
					String[] args = n.replaceFirst("/", "").split(" ");
					String[] arg = new String[args.length];
					int i = 0;
					for(String s : args){
						if(i > 0)arg[i] = s;
							i++;
					}
					for(Command c : cmds){
						c.execute(args[0], arg);
					}
				}
			}
			in.close();
		}
	}

	public static BaseServer getServer(){
		return SERVER_INSTANCE;
	}
	
	public static void main(String[] args) {
		startServer(5995);
	}

}
