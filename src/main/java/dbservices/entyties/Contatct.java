package dbservices.entyties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userdata")
public class Contatct {
    @Id
    private long id;
    @Column(name = "telegramusername")
    private String telegramUserName;

    public Contatct() {
    }

    public Contatct(String telegramUserName) {
        this.telegramUserName=telegramUserName;
    }

    @Override
    public String toString() {
        return "\nКонтакт: id="+id+", telegram="+telegramUserName;
    }
}
