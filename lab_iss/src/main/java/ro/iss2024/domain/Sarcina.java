package ro.iss2024.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Sarcina extends ro.iss2024.domain.Entity<Long>{
    LocalDateTime dataOraAtribuire;
    String descriere;
    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }
    @Column(name = "password")
    public LocalDateTime getDataOraAtribuire() {
        return dataOraAtribuire;
    }
    public Sarcina(){};

    public void setDataOraAtribuire(LocalDateTime dataOraAtribuire) {
        this.dataOraAtribuire = dataOraAtribuire;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Sarcina(Long aLong, LocalDateTime dataOraAtribuire, String descriere) {
        super(aLong);
        this.dataOraAtribuire = dataOraAtribuire;
        this.descriere = descriere;
    }

    public Sarcina(LocalDateTime dataOraAtribuire, String descriere) {
        this.dataOraAtribuire = dataOraAtribuire;
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return "Sarcina{" +
                "dataOraAtribuire=" + dataOraAtribuire +
                ", descriere='" + descriere + '\'' +
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
