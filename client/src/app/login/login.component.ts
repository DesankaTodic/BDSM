import { AuthenticationService } from '../services/authentication.service';
import { Component, OnInit} from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [AuthenticationService]
})
export class LoginComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private router: Router,
     private toastr: ToastrService) { }

  ngOnInit() {
  }

  login(loginParams, form: NgForm) {
    console.log(loginParams);
    this.authenticationService.login(loginParams)
        .subscribe(
        (data: any) => {
          if (data.status == 200) {
            localStorage.setItem('user', data.body.id);
            localStorage.setItem('category', data.body.category);
            localStorage.setItem('role', data.body.role);
            //this.toastr.success('Hello world!', 'Toastr fun!');
            window.location.reload();
          } else {
            this.toastr.error('Hello world!', 'Toastr fun!');
            form.reset();
          }
        }, () => console.log("login completed"));
  }
}
