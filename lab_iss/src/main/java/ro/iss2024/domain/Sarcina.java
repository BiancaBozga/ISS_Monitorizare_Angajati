package ro.iss2024.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Sarcina extends ro.iss2024.domain.Entity<Long>{
    LocalDateTime dataOraAtribuire;
    String descriere;
    Sef emitator ;
    Angajat receptor;

    @ManyToOne
    @JoinColumn(name = "sef_id", referencedColumnName = "id")
    public Sef getEmitator() {
        return emitator;
    }

    public void setEmitator(Sef emitator) {
        this.emitator = emitator;
    }

    @ManyToOne
    @JoinColumn(name = "angajat_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Angajat getReceptor() {
        return receptor;
    }

    public void setReceptor(Angajat receptor) {
        this.receptor = receptor;
    }
    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }
    @Column(name = "dataOraAtribuire")
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

    public Sarcina(LocalDateTime dataOraAtribuire, String descriere,Angajat angajat,Sef sef) {
        this.dataOraAtribuire = dataOraAtribuire;
        this.descriere = descriere;
        this.emitator=sef;
        this.receptor=angajat;
    }

    @Override
    public String toString() {
        return "Sarcina{" +
                "dataOraAtribuire=" + dataOraAtribuire +
                ", descriere='" + descriere + '\'' +
                ", emitator=" + emitator +
                ", receptor=" + receptor +
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
