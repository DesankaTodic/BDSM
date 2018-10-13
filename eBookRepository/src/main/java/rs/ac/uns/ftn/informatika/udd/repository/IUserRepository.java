package rs.ac.uns.ftn.informatika.udd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.udd.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username, String password);
}
