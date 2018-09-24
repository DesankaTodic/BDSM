package rs.ac.uns.ftn.informatika.udd.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "language")
@NoArgsConstructor
@Getter
@Setter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(max = 30)
    @NotEmpty
    private String name;
}
