import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  correctData: boolean = false;
  userName: string;
  userPassword: string ;
  warningVisible: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  public checkData() {
    if(this.userName == "admin" && this.userPassword == "admin") {
      this.correctData = true;
      return true;
    } else {
      return false;
    }
  }
  
  public login() {
    if(!this.checkData()) {
      this.warningVisible = true;
    }
  }
}
