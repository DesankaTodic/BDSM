package rs.ac.uns.ftn.informatika.udd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.pdf.PdfReader;

import rs.ac.uns.ftn.informatika.udd.model.Book;
import rs.ac.uns.ftn.informatika.udd.repository.IBookRepository;

@RestController
public class BookController {
	@Autowired
	IBookRepository iBookRepository;

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getBooksForCat/{id}")
	public ResponseEntity<List<Book>> getAll(@PathVariable long id) {
		List<Book> categories = new ArrayList<Book>();
		if(id == -1) {
		categories = iBookRepository.findAll();
		} else {
			categories = iBookRepository.findByCategoryId(id);
		}
		return new ResponseEntity<List<Book>>(categories, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getOneBook/{id}")
	public ResponseEntity<Book> getOne(@PathVariable long id) {
		Book book = iBookRepository.findOne(id);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(value = "/metadata")
	public HashMap<String, String> getMetadata(@RequestBody MultipartFile file) {
		PdfReader reader;
		try {
			reader = new PdfReader(file.getBytes());

			if (reader.getMetadata() == null) {
				System.out.println("null");
				HashMap<String, String> mmap = new HashMap<String, String>();
				mmap.put("NOXML", "NOXML");
				return mmap;
			} else {
				System.out.println("not null");
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = reader.getInfo();

				return map;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, String> mmap = new HashMap<String, String>();
		mmap.put("NOXML", "NOXML");
		return mmap;

	}
}
