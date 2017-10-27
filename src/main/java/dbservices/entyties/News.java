package dbservices.entyties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class News {
    @Id @Column(name = "id")
    private long id;
    @Column(name = "publicdate")
    private LocalDateTime publicdate;
    @Column(name = "name")
    private String name;
    @Lob @Column(name = "body")
    private String body;
}
