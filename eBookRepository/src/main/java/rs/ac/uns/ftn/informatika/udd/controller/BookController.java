package rs.ac.uns.ftn.informatika.udd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		if (id == -1) {
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
				HashMap<String, String> mmap = new HashMap<String, String>();
				mmap.put("NOXML", "NOXML");
				return mmap;
			} else {
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

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadBook(@PathVariable Long id) throws IOException {

		Book book = iBookRepository.findOne(id);

		if (book == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		File pdf = new File(book.getFilename());
		FileSystemResource fileResource = new FileSystemResource(pdf);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.add("filename", fileResource.getFilename());

		// convert file to byte[]
		byte[] bFile = readBytesFromFile(book.getFilename());

		return new ResponseEntity<byte[]>(bFile, headers, HttpStatus.OK);
//		return new ResponseEntity().ok().headers(headers).body(bFile);
	}

	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytesArray;

	}
}
