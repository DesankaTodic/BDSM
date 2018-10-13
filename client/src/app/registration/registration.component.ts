import { Category } from '../models/category';
import {User} from '../models/user';
import { CategoryService } from '../services/category.service';
import {UserService} from '../services/user.service';
import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [UserService, CategoryService]
})

export class RegistrationComponent implements OnInit {
  title = 'Add user';
  repeat_password = '';
  isEdit = false;
  user: User = new User();
  userId: number;
  uri: string;
  categories: Category[];
  constructor(private route: ActivatedRoute, private userService: UserService, private categoryService: CategoryService, private router: Router) {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
    this.user.categoryId = null;
  }

  ngOnInit() {
    this.initUser();
    this.initCategories();
  }

  initUser() {
    if (this.userId) {
      this.userService.getOne(this.userId).subscribe((data: any) => {
        if (data.status == 200) {
          this.user = data.body;
          this.repeat_password = this.user.password;
          this.title = 'Edit user';
          this.isEdit = true;
          //alert("Get category done!");
        } else {
          alert("smt went wrong impossible")
        }
      }, () => console.log("Get user completed"));
    } else if(this.userId === 0){
      this.user.role = 'ADMIN';
    } else {
        var userId = localStorage.getItem('user');
        this.userService.getOne(+userId).subscribe((data: any) => {
          if (data.status == 200) {
            this.user = data.body;
            this.repeat_password = this.user.password;
            this.title = 'Change your info';
            this.isEdit = true;
            //alert("Get category done!");
          } else {
            alert("smt went wrong impossible")
          }
       }, () => console.log("Get user completed"));
    }
  }
  
  initCategories() {
    this.categoryService.getAll().subscribe((data: any) => {
        if (data.status == 200) {
            this.categories = data.body;
            //alert("Get categories done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("Get categories completed"));
  }
  
  changeUserType($event: any) {
    this.user.role = $event.target.value;
    alert(this.user.role);
  }

  register() {
    if (this.repeat_password !== this.user.password) {
      alert('Passwords do not match');
      return;
    }
    if (!this.user.id) {
      this.userService.registrate(this.user).subscribe((data: any) => {
        if (data.status == 201) {
          this.router.navigateByUrl('/users');
          alert("Create user done!");
        } else {
          alert("smt went wrong impossible")
        }
      }, () => console.log("create user completed"));
    } else {
      this.userService.edit(this.user).subscribe((data: any) => {
        if (data.status == 200) {
          this.router.navigateByUrl('/users');
          alert("Edit user done!");
        } else {
          alert("smt went wrong impossible")
        }
      }, () => console.log("Edit user completed"));
    }
  }
}
