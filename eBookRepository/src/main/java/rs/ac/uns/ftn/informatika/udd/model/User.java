package rs.ac.uns.ftn.informatika.udd.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(unique = true, nullable = false)
	@Size(min = 1, max = 10)
	@NotEmpty
	@Pattern(regexp = "[\\w]{1,10}", message = "Korisnicko ime ima najvise 10 karaktera!")
	private String username;

	@Column(unique = true, nullable = false)
	@Size(min = 1, max = 10)
	@NotEmpty
	@Pattern(regexp = "[\\w]{1,20}", message = "Korisnicko ime ima najvise 10 karaktera!")
	private String firstName;

	@Column(unique = true, nullable = false)
	@Size(min = 1, max = 10)
	@NotEmpty
	@Pattern(regexp = "[\\w]{1,30}", message = "Korisnicko ime ima najvise 10 karaktera!")
	private String lastName;

	@Column(unique = true, nullable = false)
	@Email
	@NotEmpty
	private String email;

	@Column(nullable = false)
	@Size(min = 1, max = 10)
	private String password;

	@NotNull
	private TypeEnum userType;

	private Long categoryId;

	public User() {
		super();
	}

	public User(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User(Long id, String username, String email, String password, TypeEnum type) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.userType = type;

	}

	public User(String username, String firstName, String lastName, String email, String password, TypeEnum type) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userType = type;
	}
}