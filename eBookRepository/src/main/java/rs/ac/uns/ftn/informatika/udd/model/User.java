package rs.ac.uns.ftn.informatika.udd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
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

	@Column(nullable = false)
	@Size(min = 1, max = 10)
	@NotEmpty
	@Pattern(regexp = "[\\w]{1,20}", message = "Korisnicko ime ima najvise 10 karaktera!")
	private String firstName;

	@Column(nullable = false)
	@Size(min = 1, max = 10)
	@NotEmpty
	@Pattern(regexp = "[\\w]{1,30}", message = "Korisnicko ime ima najvise 10 karaktera!")
	private String lastName;

	@Column(nullable = false)
	@Email
	@NotEmpty
	private String email;

	@Column(nullable = false)
	@Size(min = 1, max = 10)
	private String password;

	@NotNull
	private TypeEnum role;

	private Long categoryId;
}