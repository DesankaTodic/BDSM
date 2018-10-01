import { UserService } from '../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [UserService]
  })
  
export class RegistrationComponent implements OnInit {
  userType = 'ADMIN';
  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }
  changeUserType($event: any) {
        this.userType = $event.target.value;
        alert(this.userType);
    }
  
  register(value, form){
    /*let x = this.userService.registrate(value).subscribe(
      res => {
        console.log(res);
        alert("You registered successfully!")
        this.router.navigateByUrl('/login');
        form.reset();
      },
      err => {
        console.log("Error occured");
      }
    );*/
    this.userService.registrate(value)
        .subscribe(
        (data: any) => {
          if (data == null) {//obrnuta je logika ako vrati praznu listu, pukao je
            alert("You registered successfully!")
            this.router.navigateByUrl('/login');
            form.reset();
          } else {
            alert("Registration failed!")
          }
        },
        () => console.log("registration completed"));
  }
}
