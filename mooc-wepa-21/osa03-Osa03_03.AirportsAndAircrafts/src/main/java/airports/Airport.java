package airports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport extends AbstractPersistable<Long> implements Serializable {

    private String identifier;
    private String name;

    @OneToMany(mappedBy = "airport")
    private List<Aircraft> aircrafts = new ArrayList<Aircraft>();

}
