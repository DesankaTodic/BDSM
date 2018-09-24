import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RepositoryComponent } from './repository/repository.component';
import { ProfileComponent } from './profile/profile.component';
import { UsersComponent } from './users/users.component';

import { EmailChangeComponent } from './email-change/email-change.component';
import { CategoryListingComponent } from './category-listing/category-listing.component';
import { BookListingComponent } from './book-listing/book-listing.component';

import { AuthenticationService } from './services/authentication/authentication.service';
import { ProfileChangeService } from './services/profile_change/profile-change.service';
import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';
import { CategoryService } from './services/category/category.service';

import { OverviewComponent } from './overview/overview.component';
import { AddBookComponent } from './add-book/add-book.component';
import { SearchComponent } from './search/search.component';
import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';

const ChildRoutes =
  [
    {
      path: "change/profile",
      component: EmailChangeComponent
    }
  ]

const RepositoryChildRoutes =
  [
    {
      path: "overview",
      component: OverviewComponent
    },
    {
      path: "add",
      component: AddBookComponent,
      canActivate: [Admin]
    },
    {
      path: "search",
      component: SearchComponent
    }
  ]

const Routes = [
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "profile",
    component: ProfileComponent,
    children: ChildRoutes,
    canActivate: [Authorized]
  },
  {
    path: "repository",
    component: RepositoryComponent,
    children: RepositoryChildRoutes
  },
  {
    path: "users",
    component: UsersComponent,
    canActivate: [Admin]
  },
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RepositoryComponent,
    ProfileComponent,
    UsersComponent,
    EmailChangeComponent,
    CategoryListingComponent,
    BookListingComponent,
    OverviewComponent,
    AddBookComponent,
    SearchComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule,
    HttpModule
  ],

  providers:  [
    Admin,
    Authorized,
    Notauthorized
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
