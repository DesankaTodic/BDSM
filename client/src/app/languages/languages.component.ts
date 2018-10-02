import { Language } from '../models/language';
import { LanguageService } from '../services/language.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-languages',
  templateUrl: './languages.component.html',
  styleUrls: ['./languages.component.css'], 
  providers: [LanguageService]
})
export class LanguagesComponent implements OnInit {
  languages: Language[];
  constructor(private router: Router, private languageService: LanguageService) { }

  ngOnInit() {
    this.languageService.getAll().subscribe((data: any) => {
        if (data.status == 200) {
            this.languages = data.body;
            alert("Get languages done!");
        } else {
          alert("smt went wrong impossible")
        }
       }, () => console.log("Get languages completed"));
     }

  edit(id: number) {
    this.router.navigate(['/languages/add', id]);
  }
}
