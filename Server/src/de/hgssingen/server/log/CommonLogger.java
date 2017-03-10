package de.hgssingen.server.log;

import java.io.*;
import java.util.*;

public class CommonLogger{

	public FileOutputStream file_stream;
	public String section;
	public PrintStream stream;
	public boolean silend;
	
	public CommonLogger(PrintStream str,String st) {
		this.section = st;
		this.stream = str;
		File fl = new File(System.getProperty("user.dir") + File.separator + "logs");
		if(fl.mkdir()){
			System.err.println(fl.getPath());
		}
		File fls = new File(fl.getPath() + File.separator + this.section + " " + getDate() + ".log");
		int i = 1;
		while(fls.exists()){
			fl = new File(fls.getPath().replace("(", "##").split("##")[0].replace(".log", "") + "(" + i + ").log");
			fls = fl;
			i++;
		}
		try {
			this.file_stream = new FileOutputStream(fls);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public void write(String x) {
		if(x == null)x = "null";
		byte[] args = x.getBytes();
        this.writeByte(args);
	}
	
	public void writeTrace(Throwable t){
		if(t == null)return;
		this.write(t.toString());
		if(t.getMessage() != null)this.write(t.getMessage());
		for(StackTraceElement e : t.getStackTrace()){
			this.write(e.toString());
		}
		this.writeTrace(t.getCause());
	}
	
	boolean write = true;
	
	private void writeByte(byte[] buf) {
		try {
		byte[] bu = String.format(getTime() + "[" + this.section + "]" + new String(buf) + "%n").getBytes();
		if(!this.silend)this.stream.print(new String(bu));
		this.file_stream.write(bu, 0, bu.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.YEAR);
	}
	
	public void setSilend(boolean silend){
		this.silend = silend;
	}
	
	public boolean isSilend(){
		return this.silend;
	}
	
	@SuppressWarnings("deprecation")
	public String getTime() {
		Date d = new Date();
		return "[" + d.getHours()+ "/" + d.getMinutes() + "/" + d.getSeconds() + "]";
	}
	
	
	
}
