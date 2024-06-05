package ro.iss2024.business;

import ro.iss2024.Observable;
import ro.iss2024.Observer;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Entity;
import ro.iss2024.domain.Sarcina;
import ro.iss2024.domain.Sef;
import ro.iss2024.domain.generalTypes.ObjectTransformer;
import ro.iss2024.persistance.hibernate.AngajatRepository;
import ro.iss2024.persistance.hibernate.SarcinaRepository;
import ro.iss2024.persistance.hibernate.SefRepository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Service implements Observable {
    private List<Observer> ob=new ArrayList<>();
    SarcinaRepository sarcinaRepository;
    AngajatRepository angajatRepository;
    SefRepository sefRepository;
    public Long save(Sef sef){
        return sefRepository.save(sef);
    }
    public Long saveAngajat(Angajat a){
        a.setPrezent(false);
        return angajatRepository.save(a);
    }
    public Angajat updateAngajat(Angajat a, Time ora){
        a.setOraSosire(ora);
        a.setPrezent(true);
        Angajat angajat_updated=angajatRepository.update(a);
        notifyObservers();
        return  angajat_updated;
    }
    public Service(SarcinaRepository sarcinaRepository, AngajatRepository angajatRepository, SefRepository sefRepository) {
        this.sarcinaRepository = sarcinaRepository;
        this.angajatRepository = angajatRepository;
        this.sefRepository = sefRepository;
    }

    public Entity<Long> checkCredential(String actualHash, String l) {
        Entity<Long> entity = sefRepository.findByUsernameandPwd(l, actualHash);
        System.out.println(entity);
        if (entity == null) {
            entity = angajatRepository.findByUsernameandPwd(l, actualHash);
        }
        return entity;
    }


    @Override
    public void addObserver(Observer o) {
        ob.add(o);
    }

    @Override
    public void removeObsrver(Observer o) {
        ob.remove(o);
    }

    @Override
    public void notifyObservers() {
        ob.forEach(Observer::update);
    }

    public Collection<Angajat> getAllAngajatiP() {
        Collection<Angajat> c=ObjectTransformer.iterableToCollection(angajatRepository.findAll());
      Collection<Angajat> c_nou= c.stream()
                .filter(Angajat::getPrezent)
                .collect(Collectors.toList());

        return ObjectTransformer.iterableToCollection(c_nou);

    }

    public Collection<Sarcina> getAllSarcini() {
        return ObjectTransformer.iterableToCollection(sarcinaRepository.findAll());
    }
    public Collection<Sarcina> getTop3Sarcini() {
        return ObjectTransformer.iterableToCollection(sarcinaRepository.findTop3ByDescriere());
    }
    public Collection<Sarcina> getSarcinibyAngajatandTime(Long angajat, LocalDateTime time) {
        return ObjectTransformer.iterableToCollection(sarcinaRepository.findSarciniByAngajatAndTime(angajat,time));
    }
    public void updateParolaAngajat(Angajat angajat, String actualHash) {
        angajat.setPassword(actualHash);
        angajatRepository.update(angajat);
        notifyObservers();
    }
    public Long saveSarcina(Sarcina sarcina){
        Long id= sarcinaRepository.save(sarcina);
        notifyObservers();
        return id;
    }
    public Angajat findAngajat(Long id){
        return angajatRepository.find(id);
    }
public Angajat deleteAngajat(Long a){
        Angajat an= angajatRepository.delete(a);
        notifyObservers();
        return an;
    }
    public void logoutAngajat(Angajat angajat) {
        angajat.setPrezent(false);
        angajatRepository.update(angajat);
        notifyObservers();
    }

    public Angajat findAngajatByusername(String username) {
        return angajatRepository.findByUsername(username);
    }

    public Collection<Angajat> getAllAngajati() {
        return ObjectTransformer.iterableToCollection(angajatRepository.findAll());
    }
}
