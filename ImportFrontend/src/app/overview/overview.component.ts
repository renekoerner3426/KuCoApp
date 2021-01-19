import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Subject } from 'rxjs';

interface DecreeEntity {
  description: string;
  state: string;
  regulations: string;
  id: number;
}

interface LastChanged {
  state: string;
  lastChange: string;
}

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  deleteUrl = "http://localhost:8081/deleteDecree";
  editUrl = "http://localhost:8081/editDecree";
  decreeUrl = "http://localhost:8081/newDecree";
  importUrl = "http://localhost:8081/maches";
  getDecreesUrl = "http://localhost:8081/decrees";

  //Filterfunction
  allStatesVisible: boolean = true;
  selectedDecreesByState = [];
  selectedDecreesByStateFiltered = [];
  descriptionDecree: string = "";
  regulationsDecree: string = "";
  searchWords;
  selectedState: string;

  //add
  missingValues: boolean = false;
  addingWasOk: boolean = false;
  addingWasfalse: boolean = false;
  existing: boolean = false;

  //Import
  selectedUploadState: string;
  importPopupVisible : boolean = false;
  failedImport: boolean = false;
  successfullImport: boolean = false;

  //Edit
  selectedDecree: DecreeEntity;
  editingDescription: string;
  editingRegulations: string;
  editingState: string;
  popupVisible: boolean = false;
  updateDecreeFailed: boolean = false;
  deleteFailed: boolean = false;

  //all
  decrees: DecreeEntity[] = [
    {id:1, state:"Bayern", description:"test1", regulations:"irgendetwas"},
    {id:2, state:"Bayern", description:"test2", regulations:"du"},
    {id:3, state:"Bayern", description:"test3", regulations:"gehts"},
    {id:4, state:"Bayern", description:"test4", regulations:"irgendetwas"},
    {id:5, state:"Bayern", description:"test5", regulations:"irgendetwas"},
  ];
  showLastChange: boolean = false;
  decreeCreated: number;
  lastUploadDate: string = "nie";

  states = ["Baden-Württemberg", "Bayern", "Berlin","Brandenburg", 
"Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern",
"Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", 
"Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.updateDecreeList();
    this.selectedDecreesByStateFiltered = this.decrees;
  }

  public updateDecreeList() {
    this.getLastChange();
    this.setMessageBooleansToFalse();
     this.http.get<DecreeEntity[]>(`http://localhost:8081/decrees`).subscribe(({
      error: error => console.error('updateDecreeList() - could not use ImportService!', error),
      next: data => data.forEach(element => {
        this.decrees.push(element);
        console.log(element);
      })
    }));
    this.decrees = this.decrees.filter((element, i) => i === this.decrees.indexOf(element))
  }

  addDecree() {
    this.getLastChange();
    this.setMessageBooleansToFalse();
    if(this.selectedState && this.descriptionDecree.length > 0) {
      let givenEntity = this.decrees.filter(decree => ((decree.description === this.descriptionDecree) && (decree.regulations === this.regulationsDecree)));
      if(givenEntity.length > 0) {
          this.existing = true;
      } else {
      let decree: DecreeEntity = {id: 0, state: this.selectedState, description: this.descriptionDecree, regulations: this.regulationsDecree }
      this.http.post<DecreeEntity>(this.decreeUrl, decree).subscribe({
       next: data => {
        this.addingWasOk = true;
         this.decreeCreated = data.id;
       },
       error: error => {
         this.addingWasfalse = true;
         this.decrees.splice(this.decrees.indexOf(decree), 1);
          this.decrees = this.decrees;
          this.searchByState(this.selectedState);
         console.error('addDecree() - could not use ImportService!', error)}}); 
         this.decrees.push(decree);
      }   
    this.decrees = this.decrees;
    this.descriptionDecree = "";
     this.regulationsDecree = "";     
     this.searchByState(this.selectedState);
    } else {
      this.missingValues = true;
    }
    
  }

  basicImport(){
    this.getLastChange();
    this.importPopupVisible = false;
     this.http.get<DecreeEntity[]>(`http://localhost:8081/maches`).subscribe(({
      error: error => console.error('basicImport() - could not use ImportService!', error)}));
  }

  getLastChange(){
    if(!this.selectedState){
      this.showLastChange = false;
    } else {
      this.showLastChange = true;
        this.http.get<LastChanged>(`http://localhost:8081/change/` + '/' + this.selectedState).subscribe(({
          error: error => console.error('getLastChanged() - could not use ImportService!', error),
          next: data => this.lastUploadDate = data.lastChange
    }));
  }
  }


  filteredImport(){
    this.getLastChange();
    this.importPopupVisible = false;
    if(!this.selectedUploadState) {
      this.basicImport();
    } else {
      this.http.get<DecreeEntity[]>(`http://localhost:8081/maches` + '/' + this.selectedUploadState).subscribe(({
        error: error => console.error('basicImport() - could not use ImportService!', error)}));
    }    
  }

  public searchByState(state: string) {
    this.getLastChange();
    if(state.length > 0) {
      this.selectedDecreesByState = this.decrees.filter(decreeEntry => decreeEntry.state === state);
    } else {
      this.selectedDecreesByState = this.decrees;
    }
    this.selectedDecreesByStateFiltered = this.selectedDecreesByState;
  }

  public searchList() {
    this.selectedDecreesByStateFiltered = this.selectedDecreesByState.filter(decreeEntry => decreeEntry.regulations.search(this.regulationsDecree));
    this.selectedDecreesByStateFiltered = this.selectedDecreesByStateFiltered.filter(decreeEntry => decreeEntry.description.search(this.descriptionDecree));

  }

  public updateDecree(){ 
    this.setMessageBooleansToFalse();
    this.getLastChange();
    let tempDescription = this.selectedDecree.description;
    let tempRegulation = this.selectedDecree.regulations;
    this.selectedDecree.description = this.editingDescription;
    this.selectedDecree.regulations = this.editingRegulations;
    this.popupVisible = false;
    return this.http.put<DecreeEntity>(this.editUrl, this.selectedDecree).subscribe({
      error: error => {
        this.updateDecreeFailed = true;
        this.selectedDecree.description = tempDescription;
        this.selectedDecree.regulations = tempRegulation;
        console.error('updateDecree() - could not use ImportService!', error)
    }});
  }

  public deleteDecree(decree: DecreeEntity){
    this.setMessageBooleansToFalse();
    this.getLastChange();
    let tempDecree = decree;
    this.decrees.splice(this.decrees.indexOf(decree), 1);
    this.http.post<DecreeEntity>(this.deleteUrl, decree).subscribe({
      error: error => {
        this.deleteFailed = true;
        this.decrees.push(tempDecree);
        console.error('deleteDecree() - could not use ImportService!', error)
    }});
    this.searchByState(this.selectedState);    
  }

  public openDecreeEditor(decree: DecreeEntity){
    this.setMessageBooleansToFalse();
    this.editingDescription = decree.description;
    this.editingRegulations = decree.regulations;
    this.editingState = decree.state;
    this.popupVisible = true;
    this.selectedDecree = decree;
  }

  public openImportPopup() {
    this.setMessageBooleansToFalse();
    this.importPopupVisible = true;
  }

  public closeImportPopup() {
    this.importPopupVisible = false;
  }

  public setMessageBooleansToFalse(){
    this.missingValues = false;
    this.addingWasOk = false;
    this.addingWasfalse = false;
    this.failedImport = false;
    this.successfullImport = false;
    this.updateDecreeFailed = false;
    this.deleteFailed = false;
    this.existing = false;
  }
}
