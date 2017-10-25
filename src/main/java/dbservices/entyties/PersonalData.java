package dbservices.entyties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personaldata")
public class PersonalData {
    @Id
    private long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;

    public PersonalData() {
    }

    public PersonalData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "\nПерсональные данные: id="+id+", имя="+firstName+", фамилия="+lastName;
    }
}
