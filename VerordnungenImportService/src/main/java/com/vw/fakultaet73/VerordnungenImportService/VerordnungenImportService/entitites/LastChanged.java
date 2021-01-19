package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites;

public class LastChanged {
	private String state;
	private String lastChange;
	
	public LastChanged(String state, String lastChange) {
		super();
		this.state = state;
		this.lastChange = lastChange;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLastChange() {
		return lastChange;
	}

	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}
}
