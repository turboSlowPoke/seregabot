package dbservices.entyties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @Id @Column(name = "id")
    private long id;
    @Column(name = "level") @NotNull
    private long level;
    @Column(name = "leftkey") @NotNull
    private long leftKey;
    @Column(name = "rightkey") @NotNull
    private long rightKey;
    @Column(name = "parentid")
    private long parentId;
    @Column(name = "chatId")
    private long chatId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Contatct contatct;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonalData personalData;
    @Column(name = "type")
    private String type = "client";

    public User() {
    }

    public User(long chatId, PersonalData personalData, Contatct contatct) {
        this.chatId=chatId;
        this.personalData = personalData;
        this.contatct=contatct;
    }

    public Contatct getContatct() {
        return this.contatct;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setLeftKey(long leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(long rightKey) {
        this.rightKey = rightKey;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    @Override
    public String toString() {
        return "\nПользователь: userID="+id+", chatId="+chatId +", level="+level +", RK="+rightKey +", LK="+leftKey;
    }
}
