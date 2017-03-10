package de.hgssingen.server;

import java.io.*;
import java.util.*;

import org.json.*;

public class Database {

	public final File DATABASE_FOLDER;
	public final HashMap<String, JSONObject> DBS = new HashMap<>();
	
	public Database(){
		this.DATABASE_FOLDER = new File(System.getProperty("user.dir") + "/database");
		this.DATABASE_FOLDER.mkdirs();
		
		this.load();
	}
	
	private void load(){
		loadToRuntime("uuid");
	}
	
	public PrintWriter creatJSONWriter(String name,boolean end){
	    PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(newDatabaseFile(name)),true);
		} catch (Exception e) {
			writer = null;
			MainServer.err.writeTrace(e);
		}
		return writer;
	}
	
	public Scanner creatScannerObject(String name){
		Scanner sc;
		try {
			sc = new Scanner(newDatabaseFile(name));
		} catch (FileNotFoundException e) {
			sc = null;
			MainServer.err.writeTrace(e);
		}
		return sc;
	}
	
	public File newDatabaseFile(String name){
		File fl = new File(this.DATABASE_FOLDER + "/" + name + ".db");
		try {
			if(!fl.exists()){
				if(!fl.createNewFile()){
					MainServer.err.writeTrace(new IOException("Unable to creat File"));
				}
				PrintWriter wr = creatJSONWriter(name, false);
				new JSONWriter(wr).object().key("id").value(name).endObject();
				wr.close();
			}
		} catch (IOException e1) {
			MainServer.err.writeTrace(e1);
		}
		return fl;
	}
	
	public boolean loadToRuntime(String db){
		if(DBS.containsKey(db)){
			return false;
		}else{
			Scanner sc = creatScannerObject(db);
			String sting = "";
			while(sc.hasNextLine()){
				sting += sc.nextLine() + String.format("%n");
			}
			sc.close();
			JSONObject json = new JSONObject(sting);
			DBS.put(db, json);
			return true;
		}
	}
	
	public Object getKeyFromDatabase(String db,String key){
		if(!DBS.containsKey(db)){
			loadToRuntime(db);
		}
		return DBS.get(db).get(key);
	}
	
    public void addValueIfNotExists(String db,String key,Object value){
    	JSONObject obj = this.DBS.get(db);
    	obj.put(key, value);
    	PrintWriter wr = creatJSONWriter(db, false);
    	obj.write(wr);
    	wr.close();
    }

}
