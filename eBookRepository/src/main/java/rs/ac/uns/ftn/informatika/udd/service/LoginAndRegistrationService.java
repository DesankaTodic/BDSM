package rs.ac.uns.ftn.informatika.udd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.udd.dto.LoginDto;
import rs.ac.uns.ftn.informatika.udd.model.User;
import rs.ac.uns.ftn.informatika.udd.repository.IUserRepository;

@Service
public class LoginAndRegistrationService implements ILoginAndRegistrationService {

    @Autowired
    IUserRepository userRepository;

    public User login(LoginDto dto) {
        return userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
    }

    public Boolean registrate(User user) {
        User saved = userRepository.save(user);
        return saved != null;
    }
}
