package ro.iss2024.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@Table(name = "boss")
public class Sef extends ro.iss2024.domain.Entity<Long> {
    String username;
    String password;

    public Sef(Long aLong, String username, String password) {
        super(aLong);
        this.username = username;
        this.password = password;
    }

    public Sef(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Sef(){}


    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sef sef = (Sef) o;
        return Objects.equals(username, sef.username) && Objects.equals(password, sef.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password);
    }

    @Override
    public String toString() {
        return "Sef{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
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
