package ro.iss2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;

import java.util.Collection;
import java.util.Observable;

public class DeleteAnagajatController implements Observer {
    @FXML
    ListView<Angajat> lista_angajati;

    ObservableList<Angajat> model  = FXCollections.observableArrayList();

    private Stage primaryStage;
    private Stage secondStage;
    Service service;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }
    private void initModel() {
        //  System.out.println(u1);
        Collection<Angajat> msg = service.getAllAngajati();
        model.setAll(msg);

    }
    public void setService(Service service1) {

        service = service1;
        service.addObserver(this);
        initModel();


    }
    @FXML
    public void initialize() {




        lista_angajati.setItems(model);

        lista_angajati.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



    }
    @FXML

    public void    stergereangjataButton (ActionEvent actionEvent) {
        Angajat a = lista_angajati.getSelectionModel().getSelectedItem();
        if (a != null) {
            try {
                service.deleteAngajat(a.getId());
            } catch (Error e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Eroare stergere angajat");
                alert.setHeaderText(null);
                alert.setContentText("Eroare: " + e);
                alert.showAndWait();
                return;
            }
        } else {
            Alert alerta1 = new Alert(Alert.AlertType.WARNING);
            alerta1.setTitle("Warning");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Nu ati selectat niciun angajat");
            alerta1.showAndWait();
        }
    }

    @Override
    public void update() {
        initModel();
    }
}
