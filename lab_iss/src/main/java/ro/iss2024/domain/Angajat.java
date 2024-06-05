package ro.iss2024.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.sql.Time;

@Entity
@Table(name = "employees")
public class Angajat extends ro.iss2024.domain.Entity<Long>{
    String  username ;
    String password;
    Time oraSosire;
    Boolean prezent;
    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    @Column(name = "oraSosire")
    public Time getOraSosire() {
        return oraSosire;
    }
    @Column(name = "prezent")
    public Boolean getPrezent() {
        return prezent;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOraSosire(Time oraSosire) {
        this.oraSosire = oraSosire;
    }

    public void setPrezent(Boolean prezent) {
        this.prezent = prezent;
    }

    public Angajat(Long aLong, String username, String password, Time oraSosire, Boolean prezent) {
        super(aLong);
        this.username = username;
        this.password = password;
        this.oraSosire = oraSosire;
        this.prezent = prezent;
    }

    public Angajat(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Angajat(String username, String password, Time oraSosire, Boolean prezent) {
        this.username = username;
        this.password = password;
        this.oraSosire = oraSosire;
        this.prezent = prezent;
    }
    public Angajat(){};

    @Override
    public String toString() {
        return "Angajat{" +
                "username='" + username + '\'' +

                ", oraSosire=" + oraSosire +
                ", prezent=" + prezent +

                '}';
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }
    @Override
    @Id
    @GeneratedValue(generator="increment")
    //   @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }
}
