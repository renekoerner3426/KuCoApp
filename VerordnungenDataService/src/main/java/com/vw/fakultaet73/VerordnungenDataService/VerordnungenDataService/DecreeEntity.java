package com.vw.fakultaet73.VerordnungenDataService.VerordnungenDataService;


/**
 * Decree
 */

public class DecreeEntity {


    private Long id = 0L;
    private String state;
    private String description;
    private String regulations;

    public DecreeEntity(String state, String description, String regulation){
        this.state = state;
        this.description = description;
        this.regulations = regulation;
    }
    
    public DecreeEntity() {
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegulations() {
		return regulations;
	}

	public void setRegulations(String regulations) {
		this.regulations = regulations;
	}
    
    
}