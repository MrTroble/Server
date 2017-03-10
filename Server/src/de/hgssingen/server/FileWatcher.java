package de.hgssingen.server;

import java.io.*;
import java.security.*;
import java.util.*;

import de.hgssingen.server.event.*;

public class FileWatcher extends File implements Runnable{
	
	private static final long serialVersionUID = 7642540239841057506L;
	
	private final MessageDigest DIGEST;
	private Thread thread;
	private final HashMap<File, Date> DATE_SET = new HashMap<>();
	private final HashMap<File, byte[]> HASH_SET = new HashMap<>();
	private final ArrayList<Runnable> QUEUE = new ArrayList<>();
	
	public FileWatcher(String file) throws IOException, NoSuchAlgorithmException {
		super(file);
		this.DIGEST = MessageDigest.getInstance("MD5");
		if(!this.exists())this.mkdirs();
		if(!this.isDirectory())throw new IllegalArgumentException("File must be a folder " + file);
		if(!this.canRead() || !this.canWrite())throw new IOException("Can't read or write");
		this.startWatcherThread();
	}

	private void startWatcherThread() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		while(true){
			for(Runnable run : QUEUE){
				run.run();
			}
			this.QUEUE.clear();
			this.updateQueue();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				MainServer.err.writeTrace(e);
			}
		}
	}
	
	private void updateQueue(){
		for(File fl : this.listFiles()){
			if(!fl.isDirectory()){
				Date dt = new Date(fl.lastModified());
				if(!DATE_SET.containsKey(fl)){
					MainServer.debug.write("Located new file " + fl.getAbsolutePath());
					this.updateDigestAndTime(fl, dt);
				}else if(!DATE_SET.get(fl).equals(dt)){
					MainServer.debug.write("Updated file " + fl.getAbsolutePath());
					this.updateDigestAndTime(fl, dt);
				}
			}
		}
	}
	
	public void updateDigestAndTime(File fl,Date d){
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				try {
					DigestInputStream digstr = new DigestInputStream(new FileInputStream(fl), DIGEST);
					Scanner bytesc = new Scanner(digstr);
					while(bytesc.hasNextByte()){bytesc.nextByte();}
					digstr.close();
					bytesc.close();
					byte[] args = DIGEST.digest();
					DIGEST.reset();
					HASH_SET.remove(fl);
					DATE_SET.remove(fl);
					HASH_SET.put(fl, args);
					DATE_SET.put(fl, d);
					MainServer.debug.write("File successfully added to hashset");
					EventSystem.INSTANCE.fire(new EventMD5Changed(fl));
				} catch (IOException e) {
					MainServer.err.writeTrace(e);
				}
			}
		};
		this.QUEUE.add(run);
	}

}
