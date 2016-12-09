package de.hgssingen.server;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

import de.hgssingen.server.command.Command;
import de.hgssingen.server.log.CommonLogger;

public class MainServer {

	private BaseServer SERVER_INSTANCE; 
	public final CommonLogger log = new CommonLogger(System.out, "INFO");
	public final CommonLogger err = new CommonLogger(System.err, "ERROR");
	public final CommonLogger debug = new CommonLogger(System.out, "DEBUG");
	public static final ArrayList<Command> cmds = new ArrayList<>();

	private static MainServer INSTANCE;
	
	public MainServer(int i){
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
		awaitAdminInput();
	}
	
	private void awaitAdminInput() {
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
					cmds.forEach(new Consumer<Command>() {
						@Override
						public void accept(Command t) {
							t.execute(args[0], arg);
						}
					});
				}
			}
			in.close();
		}
	}

	public BaseServer getServer(){
		return this.SERVER_INSTANCE;
	}
	
	public static void main(String[] args) {
		INSTANCE = new MainServer(5995);
	}
	
	public static MainServer getInstance(){
		return INSTANCE;
	}
}
