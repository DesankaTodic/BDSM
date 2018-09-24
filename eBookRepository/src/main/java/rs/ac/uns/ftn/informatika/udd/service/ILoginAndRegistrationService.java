package rs.ac.uns.ftn.informatika.udd.service;

import rs.ac.uns.ftn.informatika.udd.dto.LoginDto;
import rs.ac.uns.ftn.informatika.udd.model.User;

public interface ILoginAndRegistrationService {

    User login(LoginDto dto);
    Boolean registrate(User user);
}
