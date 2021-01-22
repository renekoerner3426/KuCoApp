package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * RegulationEntity
 */
@Document
public class DecreeEntity {
		/* @Id
	    private Long id; */
	    private String state;
	    private String description;
	    private String regulations;

	    public DecreeEntity(/* Long id, */ String state, String description, String regulation){
	    	/* this.id = id; */
	        this.state = state;
	        this.description = description;
	        this.regulations = regulation;
	    }
	    
	    public  DecreeEntity() {
	    }

		/* public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		} */

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
