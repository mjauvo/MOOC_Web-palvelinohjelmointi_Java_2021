package airports;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft extends AbstractPersistable<Long> implements Serializable {

    private String name;

    @ManyToOne
    private Airport airport;
}
