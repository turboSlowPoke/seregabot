package dbservices.entyties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "services")
public class Service {
    @Id @Column(name = "id")
    private long id;
    @Column(name = "endsubscription")
    private LocalDate endOfSubscription;

    public LocalDate getEndOfSubscription() {
        return endOfSubscription;
    }
}
