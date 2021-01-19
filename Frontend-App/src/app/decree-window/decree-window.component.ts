import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


interface DecreeEntity {
  id: number;
  description: string;
  state: string;
  regulations: string;
}

@Component({
  selector: 'app-decree-window',
  templateUrl: './decree-window.component.html',
  styleUrls: ['./decree-window.component.css']
})

@Injectable({
  providedIn: 'root'
})


export class DecreeWindowComponent implements OnInit {

  constructor(private httpClient: HttpClient) { }

  title = 'VerordnungenFrontend';
  states = ["Baden-Württemberg", "Bayern", "Berlin","Brandenburg", 
"Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern",
"Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Sarland", 
"Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"];

  decrees: DecreeEntity[] = [];


  allStatesVisible: boolean = true;
  selectedDecreesByState = [];
  selectedDecreesByStateFiltered = [];
  selectedState: string = "";
  searchWords;

  //event
  eventPopupVisible: boolean = false;
  persons: number;
  area: number;
  isOutside: boolean = false;
  maxHomesString: string;
  selectedEventState: string = "Niedersachsen";
  allowedVisible: boolean;
  notAllowedVisible: boolean;
  decreesForStateEvent: DecreeEntity[] = [];
  selectedDecreesForEventCalculation: DecreeEntity[] = [];
  calculatePerStateDecrees = [];
  maxPersonsInsideString = "Maximale Anzahl Personen innen:";
  maxPersonsOutsideString = "Maximale Anzahl Personen außen:";
  maxHomesInsideString = "Maximale Anzahl Haushalte innen:";
  maxHomesOutsideString = "Maximale Anzahl Haushalte außen:";
  maxPersonsPerAreaInsideString = "Maximale Teilnehmerzahl pro m² Fläche innen:";
  maxPersonsPerAreaOutsideString = "Maximale Teilnehmerzahl pro m² Fläche außen:";
  maxPersonsInsideNumber: number;
  maxPersonsOutsideNumber: number;
  maxPersonsPerAreaInsideNumber: number;
  maxPersonsPerAreaOutsideNumber: number;
  closedFacilitiesString = "Schließungen:";
  closedFacilities: string;

  //errorhandling
  databaseResponse:boolean = false;
  forbiddenValues: boolean = false;

  // eventOk?
  correctPeople: boolean = false;
  correctPeoplePerArea: boolean = false;




  ngOnInit() {
    this.updateDecreeList();
    this.selectedDecreesByStateFiltered = this.decrees;
  }
  
  public searchByState() {
    this.allStatesVisible = false;
    if(this.selectedState.length > 0) {
      this.selectedDecreesByState = this.decrees.filter(decreeEntry => decreeEntry.state === this.selectedState);
    } else {
      this.selectedDecreesByState = this.decrees;
    }   
    this.selectedDecreesByStateFiltered = this.selectedDecreesByState;
  }

  public searchByRegulations() {
    let tempList: DecreeEntity[] = [];
    this.selectedDecreesByStateFiltered = this.selectedDecreesByState.filter(decreeEntry => !decreeEntry.regulations.search(this.searchWords));
    tempList = this.selectedDecreesByStateFiltered;
    this.selectedDecreesByStateFiltered = this.selectedDecreesByState.filter(decreeEntry => !decreeEntry.description.search(this.searchWords));
    this.selectedDecreesByStateFiltered.forEach(decree => tempList.push(decree));
    this.selectedDecreesByStateFiltered = tempList;
  }

  public updateDecreeList() {
     this.httpClient.get<DecreeEntity[]>(`http://localhost:8082/decrees`).subscribe(({
      error: error => {
        this.databaseResponse = true;
        console.error('updateDecreeList() - could not import from database', error)},
      next: data => data.forEach(element => {
        this.decrees.push(element);
        console.log(element);
      })
    }));
    this.decrees = this.decrees.filter((element, i) => i === this.decrees.indexOf(element))
  }

  public openEventPopup() {
    this.selectedEventState = "Niedersachsen";
    this.eventPopupVisible = true;
  }

  public closeEventPopup() {
    this.eventPopupVisible = false;
    this.allowedVisible = false;
    this.notAllowedVisible = false;
    this.maxHomesString = "";
    this.closedFacilities = "";
  }

  public calculate() {
    this.decreesForStateEvent = this.decrees.filter(decree => decree.state === this.selectedEventState);
    switch(this.isOutside) {
      
      case true: {

        this.decreesForStateEvent.forEach(decree => {    

           if(!(decree.description.search(this.maxPersonsOutsideString) == -1)) {
              this.correctPeople = this.getNumber(decree.description, this.maxPersonsOutsideString ,this.persons);

          } else if (!(decree.description.search(this.maxHomesOutsideString) == -1)) {
            this.maxHomesString = decree.description;
    
          }   else if (!(decree.description.search(this.maxPersonsPerAreaOutsideString) == -1)) {
            this.correctPeoplePerArea = !this.getNumber(decree.description, this.maxPersonsPerAreaOutsideString, (this.area/this.persons));

          } else if (!(decree.description.search(this.closedFacilitiesString) == -1)) {
            this.closedFacilities = decree.description;
    
          }
        });          
          break;
      }

      case false: {

        this.decreesForStateEvent.forEach(decree => {    

          if(!(decree.description.search(this.maxPersonsInsideString) == -1)) {
            this.correctPeople = this.getNumber(decree.description, this.maxPersonsInsideString, this.persons);
    
    
          }  else if (!(decree.description.search(this.maxHomesInsideString) == -1)) {
            this.maxHomesString = decree.description;
    
          }   else if (!(decree.description.search(this.maxPersonsPerAreaInsideString) == -1)) {
            this.correctPeoplePerArea = !this.getNumber(decree.description, this.maxPersonsPerAreaInsideString, (this.area/this.persons));   

            }else if (!(decree.description.search(this.closedFacilitiesString) == -1)) {
              this.closedFacilities = decree.description;
          }
        });
          break;
      }
    }

  }

  public getNumber(description: string, text: string, compare: number ): boolean {
      description = description.replace(text, "");
      let value = parseFloat(description);
    console.log(value >= compare);
      return value >= compare;
  }
}
