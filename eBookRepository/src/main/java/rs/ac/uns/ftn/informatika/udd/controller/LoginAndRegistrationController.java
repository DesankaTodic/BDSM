package rs.ac.uns.ftn.informatika.udd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.udd.dto.LoginDto;
import rs.ac.uns.ftn.informatika.udd.model.User;
import rs.ac.uns.ftn.informatika.udd.service.ILoginAndRegistrationService;

@RestController
public class LoginAndRegistrationController {

    @Autowired
    ILoginAndRegistrationService iLoginAndRegistrationService;

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        User user = iLoginAndRegistrationService.login(loginDto);
        return user == null ?  new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("registration")
    public ResponseEntity registration(@RequestBody User user) {
        return !iLoginAndRegistrationService.registrate(user) ?  new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity(HttpStatus.CREATED);
    }
}
