package ro.iss2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sarcina;

import java.util.Collection;

public class SefController implements Observer{
    @FXML
    ListView<Angajat> lista_angajati;
    ObservableList<Angajat> model  = FXCollections.observableArrayList();

    Service service;
    private Stage primaryStage;
    private Stage secondStage;
    public void setService(Service service1) {

        service = service1;
        service.addObserver(this);
        initModel();

    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    public void initialize() {




        lista_angajati.setItems(model);


    }


    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }

    private void initModel() {
        //  System.out.println(u1);
        Collection<Angajat> msg = service.getAllAngajati();
        model.setAll(msg);

    }
    @Override
    public void update() {
        initModel();
    }
}
