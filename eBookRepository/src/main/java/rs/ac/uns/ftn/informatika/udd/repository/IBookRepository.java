package rs.ac.uns.ftn.informatika.udd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.udd.model.Book;

public interface IBookRepository extends JpaRepository<Book, Long> {

	List<Book> findByCategoryId(long id);

}
