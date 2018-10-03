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
import rs.ac.uns.ftn.informatika.udd.dto.LoginDto;
import rs.ac.uns.ftn.informatika.udd.model.Category;
import rs.ac.uns.ftn.informatika.udd.model.User;
import rs.ac.uns.ftn.informatika.udd.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.udd.service.ILoginAndRegistrationService;

@RestController
public class LoginAndRegistrationController {

    @Autowired
    ILoginAndRegistrationService iLoginAndRegistrationService;
    
    @Autowired
    IUserRepository iUserRepository;

    @SuppressWarnings("unchecked")
	@CrossOrigin
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        User user = iLoginAndRegistrationService.login(loginDto);
        return user == null ?  new ResponseEntity<User>(HttpStatus.BAD_REQUEST) : new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("registration")
    public ResponseEntity registration(@RequestBody User user) {        
		User userFromDb = iUserRepository.save(user);
		return userFromDb != null ? new ResponseEntity(HttpStatus.CREATED)
				: new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
    
	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getAllUser")
	public ResponseEntity<List<User>> getAll() {
		List<User> users = iUserRepository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("getOneUser/{id}")
	public ResponseEntity<User> getOne(@PathVariable long id) {
		User user = iUserRepository.findOne(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PutMapping("editUser")
	public ResponseEntity edit(@RequestBody User user) {
		if (user.getId() == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		User userFromDb = iUserRepository.save(user);

		return userFromDb != null ? new ResponseEntity(HttpStatus.OK)
				: new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
