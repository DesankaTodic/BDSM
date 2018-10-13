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

import rs.ac.uns.ftn.informatika.udd.model.Category;
import rs.ac.uns.ftn.informatika.udd.repository.ICategoryRepository;

@RestController
public class CategoryController {
	@Autowired
	ICategoryRepository iCategoryRepository;

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getAllCat")
	public ResponseEntity<List<Category>> getAll() {
		List<Category> categories = iCategoryRepository.findAll();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getOneCat/{id}")
	public ResponseEntity<Category> getOne(@PathVariable long id) {
		Category category = iCategoryRepository.findOne(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PostMapping("createCat")
	public ResponseEntity create(@RequestBody Category category) {
		Category categoryFromDb = iCategoryRepository.save(category);
		return categoryFromDb != null ? new ResponseEntity(HttpStatus.CREATED)
				: new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PutMapping("editCat")
	public ResponseEntity edit(@RequestBody Category category) {
		if (category.getId() == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		Category categoryFromDb = iCategoryRepository.save(category);

		return categoryFromDb != null ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
