import { AuthenticationService } from '../services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [AuthenticationService]
})
export class LoginComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
  }

  login(loginParams, form: NgForm) {
    console.log(loginParams);
    this.authenticationService.login(loginParams)
        .subscribe(
        (data: any) => {
          if (data.status == 200) {
            localStorage.setItem('user', JSON.stringify(data.body));
            localStorage.setItem('role', data.role);
            window.location.reload();
            //this.router.navigateByUrl('/repository');
            alert("Login done!")
            form.reset();
          } else {
            alert("Login failed!")
          }
        },
        () => console.log("login completed"));

    form.reset();
  }
}
