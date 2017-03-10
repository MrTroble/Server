package de.hgssingen.server.event;

public class Event {
	
	private boolean canceled = false;
	
	public boolean isCancelable(){
		return true;
	}
	
	public final boolean isCanceled(){
		return isCancelable() ? canceled:false;
	}

	public final void setCanceled(boolean canc){
		this.canceled = canc;
	}
}
