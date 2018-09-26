import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  userType = 'ADMIN';
  inviteStatus: string = 'both';
  constructor() { }

  ngOnInit() {
  }
  changeUserType($event: any) {
        this.userType = $event.target.value;
        alert(this.userType);
    }
}
