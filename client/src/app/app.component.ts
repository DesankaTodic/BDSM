import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  role: string;
  constructor(private toastr: ToastrService) {
  this.role = localStorage.getItem('role');
  }
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    localStorage.removeItem('category');
    window.location.reload();
  }

  checkIfLoggedIn() {
    return localStorage.getItem('user') != null;
  }

}
