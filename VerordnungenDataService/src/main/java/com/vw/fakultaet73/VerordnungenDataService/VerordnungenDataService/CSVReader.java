package com.vw.fakultaet73.VerordnungenDataService.VerordnungenDataService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CSVReader {


	InputStream input;

	public List<DecreeEntity> getDecreesList() {
		List<DecreeEntity> decreeList = new ArrayList<>();
		List<String[]> decreesListAsString = new ArrayList<>();
		decreesListAsString = readFromCSV();
		for (String[] decree : decreesListAsString) {
			System.out.println(decree);
			if(decree.length < 3) {
				decreeList.add(new DecreeEntity(decree[1].trim(), decree[0].trim(), "-"));
			} else {
				decreeList.add(new DecreeEntity(decree[1].trim(), decree[0].trim(), decree[2].trim()));
			}
		}
		return decreeList;
	}


	public List<String[]> readFromCSV(){		
		List<String[]> decreeList = new ArrayList<>();
		StringBuilder sbAllDecrees = new StringBuilder();
		try {
			sbAllDecrees = StringBuilderFromCSV();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] decreesString = seperateDecrees(sbAllDecrees);
		for (String string : decreesString) {
			string = string.replace("\"", "");
			decreeList.add(createDecreeStringArray(string));
		   }
		 
		return decreeList;
	}

	private StringBuilder StringBuilderFromCSV() throws IOException {
		input = this.getClass().getResourceAsStream("Verordnungen.csv");
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br =  new BufferedReader(new InputStreamReader(this.input, "UTF-8"));
		while((line = br.readLine()) != null) {
				sb.append(line + "#");
				}	
		return sb;
	}

	private String[] seperateDecrees(StringBuilder sbAllDecrees) {
		return sbAllDecrees.toString().split("#");
	}

	private String[] createDecreeStringArray(String string) {
		String[] decree = string.split(";");
		return decree;
	}
}
	
	
	   

