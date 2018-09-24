package rs.ac.uns.ftn.informatika.udd.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDto {

    @Size(min = 1, max = 10)
    private String password;

    @Size(min = 1, max = 10)
    @NotEmpty
    @Pattern(regexp = "[\\w]{1,10}", message = "Korisnicko ime ima najvise 10 karaktera!")
    private String username;
}
