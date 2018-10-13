package rs.ac.uns.ftn.informatika.udd.lucene.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadModel {

	private Long id;

	private String title;

	private String keywords;

	private String author;

	private Long language;

	private Long category;

	private MultipartFile[] files;

	@Override
	public String toString() {
		return "UploadModel{" + "title='" + title + '\'' + ", files=" + Arrays.toString(files) + '}';
	}
}
