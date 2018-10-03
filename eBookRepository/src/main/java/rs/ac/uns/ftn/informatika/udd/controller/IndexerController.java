package rs.ac.uns.ftn.informatika.udd.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.informatika.udd.lucene.indexing.Indexer;
import rs.ac.uns.ftn.informatika.udd.lucene.model.IndexUnit;
import rs.ac.uns.ftn.informatika.udd.lucene.model.UploadModel;
import rs.ac.uns.ftn.informatika.udd.model.Book;
import rs.ac.uns.ftn.informatika.udd.repository.IBookRepository;
import rs.ac.uns.ftn.informatika.udd.repository.ICategoryRepository;
import rs.ac.uns.ftn.informatika.udd.repository.ILanguageRepository;

@RestController
public class IndexerController {
	@Autowired
	IBookRepository iBookRepository;
	@Autowired
	ICategoryRepository iCategoryRepository;
	@Autowired
	ILanguageRepository iLanguageRepository;
	
	private static String DATA_DIR_PATH;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		DATA_DIR_PATH = rb.getString("dataDir");
	}

	@Autowired
	private Indexer indexer;

	@GetMapping("/reindex")
	public ResponseEntity<String> index() throws IOException {
		File dataDir = getResourceFilePath(DATA_DIR_PATH);
		long start = new Date().getTime();
		int numIndexed = indexer.index(dataDir);
		long end = new Date().getTime();
		String text = "Indexing " + numIndexed + " files took " + (end - start) + " milliseconds";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}

	private File getResourceFilePath(String path) {
		URL url = this.getClass().getClassLoader().getResource(path);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			file = new File(url.getPath());
		}
		return file;
	}

	@PostMapping("/index/add")
	public ResponseEntity<String> multiUploadFileModel(@ModelAttribute UploadModel model) {

		try {

			indexUploadedFile(model);

		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

	}

	// save file
	private String saveUploadedFile(MultipartFile file) throws IOException {
		String retVal = null;
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(
					getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.write(path, bytes);
			retVal = path.toString();
		}
		return retVal;
	}

	private void indexUploadedFile(UploadModel model) throws IOException {

		for (MultipartFile file : model.getFiles()) {

			if (file.isEmpty()) {
				continue; // next please
			}
			String fileName = saveUploadedFile(file);
			if (fileName != null) {
				IndexUnit indexUnit = indexer.getHandler(fileName).getIndexUnit(new File(fileName));
				Book book = new Book();
				book.setAuthor(model.getAuthor());
				book.setTitle(model.getTitle());
				book.setFilename(fileName);
				book.setKeywords(model.getKeywords());
				book.setCategoryName(iCategoryRepository.getOne(model.getCategory()).getName());
				book.setLanguageName(iLanguageRepository.getOne(model.getLanguage()).getName());
				book = iBookRepository.save(book);

				indexUnit.setTitle(book.getTitle());
				indexUnit.setKeywords(book.getKeywords());
				indexUnit.setAuthor(book.getAuthor());
				indexUnit.setFilename(book.getFilename());
				indexUnit.setCategory(book.getCategoryName());
				indexUnit.setLanguage(book.getLanguageName());
				indexUnit.setInternalId(book.getId());

				indexer.add(indexUnit);
			}
		}
	}
	
	@PostMapping("/update/metadata")
	public ResponseEntity<Book> metadataUpdate(@RequestBody Book book) {
		book.setCategoryName(iCategoryRepository.getOne(book.getCategoryId()).getName());
		book.setLanguageName(iLanguageRepository.getOne(book.getLanguageId()).getName());
		book = iBookRepository.save(book);

		try {
			reindexUploadedFile(book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);

	}
	
	private void reindexUploadedFile(Book book) throws IOException {

		IndexUnit indexUnit = indexer.getHandler(book.getFilename()).getIndexUnit(new File(book.getFilename()));

		indexUnit.setTitle(book.getTitle());
		indexUnit.setKeywords(book.getKeywords());
		indexUnit.setAuthor(book.getAuthor());
		indexUnit.setFilename(book.getFilename());
		indexUnit.setLanguage(book.getLanguageName());
		indexUnit.setCategory(book.getCategoryName());
		indexUnit.setInternalId(book.getId());

		indexer.delete(book.getFilename());
		indexer.add(indexUnit);
	}

}
