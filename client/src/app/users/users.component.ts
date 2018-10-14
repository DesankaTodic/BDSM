import { User } from '../models/user';
import { UserService } from '../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  providers: [UserService]
})
export class UsersComponent implements OnInit {
  users: User[];
  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    this.userService.getAll().subscribe((data: any) => {
        if (data.status == 200) {
            this.users = data.body;
            //alert("Get categories done!");
        } else {
          alert("Failed. Try again.")
        }
       }, () => console.log("Get users completed"));
     }

  edit(id: number) {
    this.router.navigate(['/registration', id]);
  }
}
