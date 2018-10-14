import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { RegistrationComponent } from './registration/registration.component';
import { BooksComponent } from './books/books.component';
import { AddBookComponent } from './add-book/add-book.component';
import { CategoriesComponent } from './categories/categories.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { LanguagesComponent } from './languages/languages.component';
import { AddLanguageComponent } from './add-language/add-language.component';
import { UsersComponent } from './users/users.component';
import { SearchComponent } from './search/search.component';

const Routes = [
    {
        path: "login",
        component: LoginComponent,
        canActivate: [Notauthorized]
    },
  {
        path: "registration/:id",
        component: RegistrationComponent,
        canActivate: [Admin]
    },
  {
        path: "users",
        component: UsersComponent,
        canActivate: [Admin]
    },
  {
        path: "books",
        component: BooksComponent
    },
  {
      path: "books/add/:id",
      component: AddBookComponent,
      canActivate: [Admin]
    },
  {
        path: "categories",
        component: CategoriesComponent,
        canActivate: [Authorized]
    },
    {
        path: "categories/add/:id",
        component: AddCategoryComponent,
        canActivate: [Admin]
    }
    ,
  {
        path: "languages",
        component: LanguagesComponent,
        canActivate: [Admin]
    },
    {
        path: "languages/add/:id",
        component: AddLanguageComponent,
        canActivate: [Admin]
    },
    {
        path: "profile",
        component: RegistrationComponent,
        canActivate: [Authorized]
    },
    {
        path: "search",
        component: SearchComponent
    }
    ]
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    BooksComponent,
    AddBookComponent,
    CategoriesComponent,
    AddCategoryComponent,
    LanguagesComponent,
    AddLanguageComponent,
    UsersComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
      FormsModule,
      RouterModule.forRoot(Routes),
      HttpClientModule,
      HttpModule,
      BrowserAnimationsModule,
      ToastrModule.forRoot({
      preventDuplicates: true, timeOut: 1000})
  ],
  providers: [
      Admin,
      Authorized,
      Notauthorized
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
