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

const Routes = [
    {
        path: "login",
        component: LoginComponent
    }
    ]
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
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
