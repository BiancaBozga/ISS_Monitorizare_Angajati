package ro.iss2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sarcina;
import ro.iss2024.domain.Sef;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SefController implements Observer{
    Sef sef;
    @FXML
    TextArea descriere;
    @FXML
    ListView<Angajat> lista_angajati;
    @FXML
    ListView<String> lista_sarcini;
    ObservableList<Angajat> model  = FXCollections.observableArrayList();
    ObservableList<String> model2  = FXCollections.observableArrayList();

    Service service;
    private Stage primaryStage;
    private Stage secondStage;
    public void setService(Service service1) {

        service = service1;
        service.addObserver(this);
        initModel();
        initModel2();

    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    public void initialize() {




        lista_angajati.setItems(model);
        lista_sarcini.setItems(model2);
        lista_angajati.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lista_sarcini.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }


    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }

    private void initModel() {
        //  System.out.println(u1);
        Collection<Angajat> msg = service.getAllAngajatiP();
        model.setAll(msg);

    }
    private void initModel2() {
        //  System.out.println(u1);
        Collection<Sarcina> top3Sarcini = service.getTop3Sarcini();
        List<String> descrieri = top3Sarcini.stream()
                .map(Sarcina::getDescriere)
                .collect(Collectors.toList());
        model2.setAll(descrieri);

    }
    @Override
    public void update() {

        initModel();
        initModel2();

    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML

    public void InregistarreAngajatButton (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/crearecont_angajat.fxml"));
        Parent root = loader.load();
        CreareContangajatController Controller = loader.getController();
        // Set necessary services and attributes
        Controller.setService(service);

        // Create and show the new stage
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        Controller.setPrimaryStage(newStage);

        newStage.show();
    }
    @FXML

    public void atribuiteSarcinaButton(ActionEvent actionEvent) {
        String des=descriere.getText();
        Angajat a=lista_angajati.getSelectionModel().getSelectedItem();
        String s=lista_sarcini.getSelectionModel().getSelectedItem();
        System.out.println(sef.getId());

        if(a==null){
            Alert alerta1 = new Alert(Alert.AlertType.WARNING);
            alerta1.setTitle("Warning");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Nu ati selectat niciun angajat");
            alerta1.showAndWait();
        }
        else if (s==null && des!=""){
            try{
                service.saveSarcina(new Sarcina(LocalDateTime.now(),des,a,sef));
                Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
                alerta1.setTitle("Info");
                alerta1.setHeaderText(null);
                alerta1.setContentText("Sarcina atribuita cu succes!");
                alerta1.showAndWait();
                initModel2();


        } catch (Exception e) {
            System.out.println("Eroare la atribuita sarcina: " + e.getMessage());
        }
        }
        else if (s!=null){
            try{
                service.saveSarcina(new Sarcina(LocalDateTime.now(),s,a,sef));
                Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
                alerta1.setTitle("Info");
                alerta1.setHeaderText(null);
                alerta1.setContentText("Sarcina atribuita cu succes!");
                alerta1.showAndWait();
                initModel2();

        } catch (Exception e) {
            System.out.println("Eroare la atribuire sarcina: " + e.getMessage());
        }
        }
        else{
            Alert alerta1 = new Alert(Alert.AlertType.WARNING);
            alerta1.setTitle("Warning");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Ori selectati o sarcina ori introduceti o descriere");
            alerta1.showAndWait();
        }

    }

    public void setSef(Sef user) {
        this.sef=user;
    }
    @FXML

    public void deleteAngajat(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/delete_anagajat.fxml"));
        Parent root = loader.load();
        DeleteAnagajatController Controller = loader.getController();
        // Set necessary services and attributes
        Controller.setService(service);

        // Create and show the new stage
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        Controller.setPrimaryStage(newStage);

        newStage.show();

    }
}
