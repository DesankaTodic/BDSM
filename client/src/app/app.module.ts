import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

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

const Routes = [
    {
        path: "login",
        component: LoginComponent
    },
  {
        path: "registration/:id",
        component: RegistrationComponent
    },
  {
        path: "users",
        component: UsersComponent
    },
  {
        path: "books",
        component: BooksComponent
    },
  {
      path: "books/add/:id",
      component: AddBookComponent
    },
  {
        path: "categories",
        component: CategoriesComponent
    },
    {
        path: "categories/add/:id",
        component: AddCategoryComponent
    }
    ,
  {
        path: "languages",
        component: LanguagesComponent
    },
    {
        path: "languages/add/:id",
        component: AddLanguageComponent
    },
    {
        path: "profile",
        component: RegistrationComponent
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
    UsersComponent
  ],
  imports: [
    BrowserModule,
      FormsModule,
      RouterModule.forRoot(Routes),
      HttpClientModule,
      HttpModule
  ],
  providers: [
      Admin,
      Authorized,
      Notauthorized
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
