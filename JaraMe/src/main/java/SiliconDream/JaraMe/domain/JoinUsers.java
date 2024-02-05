package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity

@Table
public class JoinUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinUsersId;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    @ManyToOne
    @JoinColumn(name="jaraUs")
    private JaraUs jaraUs;


    @Column(name = "signUpDate")
    private LocalDate signUpDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinUsers that = (JoinUsers) o;

        return Objects.equals(user, that.user) &&
                Objects.equals(jaraUs, that.jaraUs) &&
                Objects.equals(signUpDate, that.signUpDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, jaraUs, signUpDate);
    }
    //TODO: getter and setter

    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(LocalDate signUpDate) {
        this.signUpDate = signUpDate;
    }

    public Long getJoinUsersId() {
        return joinUsersId;
    }

    public void setJoinUsersId(Long joinUsersId) {
        this.joinUsersId = joinUsersId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JaraUs getJaraUs() {
        return jaraUs;
    }

    public void setJaraUs(JaraUs jaraUs) {
        this.jaraUs = jaraUs;
    }

}


