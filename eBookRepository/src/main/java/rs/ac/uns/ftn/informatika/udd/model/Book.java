package rs.ac.uns.ftn.informatika.udd.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    @Size(max = 80)
    @NotEmpty
    private String title;

    @Size(max = 120)
    private String author;

    @Size(max = 80)
    private String keywords;

    private int publicationYear;

    @Column(nullable = false)
    @Size(max = 200)
    @NotEmpty
    private String filename;

    @Size(max = 100)
    private String mime;

    private Long languageId;

    private Long categoryId;

    public Book() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Book(String title, String author, String keywords, int publicationYear, String filename, String mime) {
        super();
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.publicationYear = publicationYear;
        this.filename = filename;
        this.mime = mime;
    }

    public Book(Long id, String title, String author, String keywords, int publicationYear, String filename,
                String mime, Long languageId, Long categoryId) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.publicationYear = publicationYear;
        this.filename = filename;
        this.mime = mime;
        this.languageId = languageId;
        this.categoryId = categoryId;
    }

}
