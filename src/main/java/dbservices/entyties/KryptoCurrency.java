package dbservices.entyties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "currencies")
public class KryptoCurrency {
    @Id @Column(name = "id")
    private long id;
    @Column(name = "addtime")
    private LocalDateTime addTime;
    @Column(name = "name")
    private String name;
    @Column(name = "shortname")
    private String shortName;
    @Lob @Column(name = "descroption")
    private String description;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }
}
