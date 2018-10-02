package rs.ac.uns.ftn.informatika.udd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.udd.model.Language;
import rs.ac.uns.ftn.informatika.udd.repository.ILanguageRepository;

@RestController
public class LanguageController {
	@Autowired
	ILanguageRepository iLanguageRepository;

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getAllLan")
	public ResponseEntity<List<Language>> getAll() {
		List<Language> languages = iLanguageRepository.findAll();
		return new ResponseEntity<List<Language>>(languages, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getOneLan/{id}")
	public ResponseEntity<Language> getOne(@PathVariable long id) {
		Language language = iLanguageRepository.findOne(id);
		return new ResponseEntity<Language>(language, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PostMapping("createLan")
	public ResponseEntity create(@RequestBody Language language) {
		Language languageFromDb = iLanguageRepository.save(language);
		return languageFromDb != null ? new ResponseEntity(HttpStatus.CREATED)
				: new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PutMapping("editLan")
	public ResponseEntity edit(@RequestBody Language language) {
		if (language.getId() == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		Language languageFromDb = iLanguageRepository.save(language);

		return languageFromDb != null ? new ResponseEntity(HttpStatus.OK)
				: new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
