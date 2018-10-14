import { Category } from '../models/category';
import {User} from '../models/user';
import { CategoryService } from '../services/category.service';
import {UserService} from '../services/user.service';
import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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
  constructor(private route: ActivatedRoute, private userService: UserService, private categoryService: CategoryService, private router: Router, private toastr: ToastrService) {
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
        } else {
          this.toastr.error('User get failed!', 'Error!');
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
            this.title = 'Update profile';
            this.isEdit = true;
          } else {
            this.toastr.error('User get failed!', 'Error!');
          }
       }, () => console.log("Get user completed"));
    }
  }

  initCategories() {
    this.categoryService.getAll().subscribe((data: any) => {
        if (data.status == 200) {
            this.categories = data.body;
        } else {
          this.toastr.error('Category get failed!', 'Error!');
        }
       }, () => console.log("Get categories completed"));
  }

  changeUserType($event: any) {
    this.user.role = $event.target.value;
    alert(this.user.role);
  }

  register() {
    if (this.repeat_password !== this.user.password) {
      this.toastr.error('Passwords should match!', 'Error!');
      return;
    }
    if (!this.user.id) {
      this.userService.registrate(this.user).subscribe((data: any) => {
        if (data.status == 201) {
          this.router.navigateByUrl('/users');
          this.toastr.success('User save success!', 'Success!');
        } else {
          this.toastr.error('User save failed!', 'Error!');
        }
      }, () => console.log("create user completed"));
    } else {
      this.userService.edit(this.user).subscribe((data: any) => {
        if (data.status == 200) {
          this.router.navigateByUrl('/users');
          this.toastr.success('User edit success!', 'Success!');
        } else {
          this.toastr.error('User edit failed!', 'Error!');
        }
      }, () => console.log("Edit user completed"));
    }
  }
}
