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

const Routes = [
    {
        path: "login",
        component: LoginComponent
    },
  {
        path: "registration",
        component: RegistrationComponent
    },
  {
        path: "books",
        component: BooksComponent
    },
  {
      path: "books/add",
      component: AddBookComponent
    },
  {
        path: "categories",
        component: CategoriesComponent
    },
    {
        path: "categories/add",
        component: AddCategoryComponent
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
    AddCategoryComponent
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
