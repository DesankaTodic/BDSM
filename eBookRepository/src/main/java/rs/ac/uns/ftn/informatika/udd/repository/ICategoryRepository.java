package rs.ac.uns.ftn.informatika.udd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.udd.model.Category;

public interface ICategoryRepository  extends JpaRepository<Category, Long>{

}
