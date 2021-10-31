package simplebanking;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> implements Serializable {

    private String iban;
    private Integer balance;

    // TÄTÄ EI OLE TEHTÄVÄNANNOSSA
    @ManyToOne
    private Client client;
}
