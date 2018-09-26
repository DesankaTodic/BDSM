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

const Routes = [
    {
        path: "login",
        component: LoginComponent
    },
  {
        path: "registration",
        component: RegistrationComponent
    }
    ]
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent
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
