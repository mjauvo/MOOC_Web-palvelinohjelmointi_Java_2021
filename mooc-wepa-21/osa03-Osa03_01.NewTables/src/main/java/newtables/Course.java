package newtables;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course extends AbstractPersistable<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}
