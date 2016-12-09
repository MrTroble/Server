package de.hgssingen.server.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

public class CommonLogger extends PrintStream{

	public FileOutputStream file_stream;
	public String section;
	
	public CommonLogger(OutputStream str,String st) {
		super(str);
		this.section = st;
		File fl = new File(System.getProperty("user.dir") + File.separator + "logs");
		if(fl.mkdir()){
			System.err.println(fl.getPath());
			throw new NullPointerException("Directory Creation failed");
		}
		fl = new File(fl.getPath() + File.separator + this.section + " " + getDate() + ".log");
		int i = 1;
		while(fl.exists()){
			fl = new File(fl.getPath().replace("(", ":").split(":")[0].replace(".log", "") + "(" + i + ").log");
			i++;
		}
		try {
			this.file_stream = new FileOutputStream(fl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void println(String x) {
		byte[] args = x.getBytes();
        this.write(args, 0, args.length);
	}
	
	@Override
	public void print(String s) {
		byte[] args = s.getBytes();
        this.write(args, 0, args.length);
	}
	
	public void printTrace(Throwable t){
		this.print(t.toString());
		this.print(t.getMessage());
		for(StackTraceElement e : t.getStackTrace()){
			this.print(e.toString());
		}
		this.printTrace(t.getCause());
	}
	
	boolean write = true;
	
	@Override
	public void write(byte[] buf, int off, int len) {
		synchronized (this) {
			if(write){
				write = false;
			}else{
				write = true;
				return;
			}
		try {
		byte[] bu = (getTime() + "[" + this.section + "]" + new String(buf) + "\n").getBytes();
		this.out.write(bu, off, bu.length);
		this.file_stream.write(bu, off, bu.length);
		} catch (IOException e) {
			e.printStackTrace(this);
		}
		}
	}
	
	public String getDate() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.YEAR);
	}
	
	@SuppressWarnings("deprecation")
	public String getTime() {
		Date d = new Date();
		return "[" + d.getHours()+ "/" + d.getMinutes() + "/" + d.getSeconds() + "]";
	}
	
	
	
}
