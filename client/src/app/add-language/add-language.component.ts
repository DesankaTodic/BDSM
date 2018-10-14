import { Language } from '../models/language';
import { LanguageService } from '../services/language.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-language',
  templateUrl: './add-language.component.html',
  styleUrls: ['./add-language.component.css'],
  providers: [LanguageService]
})
export class AddLanguageComponent implements OnInit {
  title = 'Add language';
  lanId: number;
  language: Language = new Language();
  constructor(private route: ActivatedRoute,private languageService: LanguageService, private router: Router) { 
  this.route.params.subscribe(params => {
      this.lanId = +params['id'];
    });
  }

  ngOnInit() {
    if(this.lanId) {
      this.languageService.getOne(this.lanId).subscribe((data: any) => {
        if (data.status == 200) {
            this.language = data.body;
            this.title = 'Edit language';
        } else {
          alert("Failed. Try again.")
        }
       }, () => console.log("Get language completed"));
     }
    }

   save() {
     if(!this.language.id) {
       this.languageService.create(this.language).subscribe((data: any) => {
        if (data.status == 201) {
            this.router.navigateByUrl('/languages');
        } else {
          alert("Failed. Try again.")
        }
       }, () => console.log("create language completed"));
     } else {
        this.languageService.edit(this.language).subscribe((data: any) => {
        if (data.status == 200) {
            this.router.navigateByUrl('/languages');
        } else {
          alert("Failed. Try again.")
        }
       }, () => console.log("Edit language completed"));
     }
   } 
}
