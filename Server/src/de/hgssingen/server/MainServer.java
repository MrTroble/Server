package de.hgssingen.server;

import java.util.ArrayList;
import java.util.Scanner;

import de.hgssingen.server.command.Command;
import de.hgssingen.server.command.CommandSetRoll;
import de.hgssingen.server.log.CommonLogger;

public class MainServer {

	public static final String SEPERATOR = "[&m$]";
	
	private static BaseServer SERVER_INSTANCE; 
	public static CommonLogger log;
	public static CommonLogger err;
	public static CommonLogger debug;
	public static final ArrayList<Command> cmds = new ArrayList<>();

	public static Database DATABASE = new Database();
	
	public static void startServer(int i){
		
		cmds.add(new CommandSetRoll());
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				log.write("Server started on port " + i);
				try{
				SERVER_INSTANCE = new BaseServer(i);
				}catch(Throwable t){
					err.write("Server failed loading");
					err.writeTrace(t);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.gc();
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						log.writeTrace(e);
					}
				}
			}
		}).start();
		log.write("Await admin input...");
		awaitAdminInput();
	}
	
	private static void awaitAdminInput() {
		Scanner in = new Scanner(System.in);
		while(in.hasNextLine()){
			String n = in.nextLine();
			if(n.startsWith("/")){
				String[] args = n.replaceFirst("/", "").split(" ");
				String[] arg = new String[args.length - 1];
				int i = 0;
			    for(String s : args){   
					if(i != 0)arg[i - 1] = s;
					i++;
				}
				for(Command c : cmds){
					c.execute(args[0], arg);
				}
			}
		}
		in.close();
	}

	public static BaseServer getServer(){
		return SERVER_INSTANCE;
	}
	
	public static void main(String[] args) {
		log = new CommonLogger(System.out, "INFO");
		err = new CommonLogger(System.err, "ERROR");
		debug = new CommonLogger(System.out, "DEBUG");
		startServer(5995);
	}

}
