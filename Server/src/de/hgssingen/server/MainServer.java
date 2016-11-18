package de.hgssingen.server;

import de.hgssingen.server.log.CommonLogger;

public class MainServer {

	private BaseServer SERVER_INSTANCE; 
	public final CommonLogger log = new CommonLogger(System.out, "INFO");
	public final CommonLogger err = new CommonLogger(System.err, "ERROR");
	public final CommonLogger debug = new CommonLogger(System.err, "DEBUG");

	private static MainServer INSTANCE;
	
	public MainServer(int i){
		log.println("Server Beginn Loading");
		try{
		this.SERVER_INSTANCE = new BaseServer(i);
		}catch(Throwable t){
			err.println("Server Failed Loading");
			t.printStackTrace(err);
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
