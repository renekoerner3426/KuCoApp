package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LastChangedStorage {
	private List<LastChanged> list = new ArrayList<>();
	
	public LastChangedStorage() {
		this.list.add(new LastChanged("Baden-Württemberg", "nie"));
		this.list.add(new LastChanged("Bayern", "nie"));
		this.list.add(new LastChanged("Berlin", "nie"));
		this.list.add(new LastChanged("Brandenburg", "nie"));
		this.list.add(new LastChanged("Bremen", "nie"));
		this.list.add(new LastChanged("Hamburg", "nie"));
		this.list.add(new LastChanged("Hessen", "nie"));
		this.list.add(new LastChanged("Mecklenburg-Vorpommern", "nie"));
		this.list.add(new LastChanged("Niedersachsen", "nie"));
		this.list.add(new LastChanged("Nordrhein-Westfalen", "nie"));
		this.list.add(new LastChanged("Rheinland-Pfalz", "never"));
		this.list.add(new LastChanged("Sarland", "nie"));
		this.list.add(new LastChanged("Sachsen", "nie"));
		this.list.add(new LastChanged("Sachsen-Anhalt", "nie"));
		this.list.add(new LastChanged("Schleswig-Holstein", "nie"));
		this.list.add(new LastChanged("Thüringen", "nie"));
	}
	
	public void change(String state) {
		for (LastChanged lastChanged : this.list) {
			if (lastChanged.getState().equals(state)) {
				lastChanged.setLastChange("" + LocalDateTime.now(ZoneId.of("Europe/Berlin")));
			}
		}
	}
	
	public void changeAll() {
		for (LastChanged lastChanged : this.list) {
				lastChanged.setLastChange("" + LocalDateTime.now(ZoneId.of("Europe/Berlin")));
		}
	}
	
	public LastChanged getLastChangedByState(String state) {
		for (LastChanged lastChanged : this.list) {
			if (lastChanged.getState().equals(state)) {
				return lastChanged;
			}
		}
		return null;
	}

}
