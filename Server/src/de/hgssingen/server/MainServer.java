package de.hgssingen.server;

import java.io.*;
import java.util.*;

import de.hgssingen.server.command.*;
import de.hgssingen.server.log.*;

public class MainServer {

	public static final String SEPERATOR = "#";
	
	private static BaseServer SERVER_INSTANCE; 
	public static CommonLogger log;
	public static CommonLogger err;
	public static CommonLogger debug;
	public static final ArrayList<Command> cmds = new ArrayList<>();
	
	public static Database DATABASE = new Database();
	public static FileWatcher WATCHER;
	
	public static void startServer(int i){
		
		cmds.add(new CommandSetRoll());
		cmds.add(new CommandHelp());
		
		try {
			WATCHER = new FileWatcher(DATABASE.DATABASE_FOLDER.getAbsolutePath() + File.separator  + "Files");
		} catch (Throwable e1) {
			err.writeTrace(e1);
		}
		
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
