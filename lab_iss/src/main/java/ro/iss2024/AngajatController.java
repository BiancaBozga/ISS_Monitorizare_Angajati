package ro.iss2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sarcina;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AngajatController implements Observer {
    LocalDateTime timp_prezent;
    Service service;
    Angajat angajat;
    private Stage primaryStage;
    private Stage secondStage;
    @FXML
    PasswordField parola_noua;
    @FXML
    ComboBox<Integer> ora;
    @FXML
    ComboBox<Integer> minute;
    @FXML
    ComboBox<Integer> sec;
    @FXML
    ListView<Sarcina> lista_sarcini;
    ObservableList<Sarcina> model = FXCollections.observableArrayList();

    public void setService(Service service1) {

        service = service1;
        service.addObserver(this);

    }

    public void setAngajat(Angajat a) {
        angajat = a;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }


    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }

    @FXML
    public void initialize() {
        ObservableList<Integer> oraList = FXCollections.observableArrayList();
        for (int i = 0; i <= 23; i++) {
            oraList.add(i);
        }
        ObservableList<Integer> minute_secunde_List = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            minute_secunde_List.add(i);
        }

        ora.setItems(oraList);
        minute.setItems(minute_secunde_List);
        sec.setItems(minute_secunde_List);


        ora.getSelectionModel().selectFirst();
        minute.getSelectionModel().selectFirst();
        sec.getSelectionModel().selectFirst();
        lista_sarcini.setItems(model);

    }

    private void initModel() {
        Collection<Sarcina> msg = service.getSarcinibyAngajatandTime(angajat.getId(),timp_prezent);
        model.setAll(msg);

    }

    @Override
    public void update() {
        initModel();
        Angajat a=service.findAngajat(angajat.getId());
        System.out.println(angajat.getId());
        if(a==null )
        {

//            logout();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contul a fost sters");
            alert.setHeaderText(null);
            alert.setContentText("Nu mai exista aceste credentiale/Contul a fost dezactivat");
            alert.showAndWait();
primaryStage.close();

        }
//        else if(!a.getPrezent()){
//            primaryStage.close();
//        }


    }

    private Angajat getAngajat() {
        return this.angajat;
    }

    @FXML
    private void handleInregsitrarePrezent(ActionEvent event) {
        String oras = ora.getSelectionModel().getSelectedItem() + ":" + minute.getSelectionModel().getSelectedItem() + ":" +
                sec.getSelectionModel().getSelectedItem();

        service.updateAngajat(getAngajat(), Time.valueOf(oras));
        timp_prezent=LocalDateTime.of(LocalDate.now(), LocalTime.of(ora.getSelectionModel().getSelectedItem(),
                minute.getSelectionModel().getSelectedItem() ,  sec.getSelectionModel().getSelectedItem()));
        initModel();
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }

    @FXML
    private void handleModificareParola(ActionEvent actionEvent) {
        String newPassword = parola_noua.getText();

        if (newPassword.isEmpty()) {
            // Afișează un mesaj de alertă dacă parola este goală
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Parola invalida");
            alert.setHeaderText(null);
            alert.setContentText("Parola noua nu poate fi goala!");
            alert.showAndWait();
            return;
        }
        try {
            // Hash the password
            String passwd1 = parola_noua.getText();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwd1.getBytes());
            byte[] digest = md.digest();
            String actualHash = bytesToHex(digest).toUpperCase();
            System.out.println(passwd1);
            System.out.println(actualHash);

            service.updateParolaAngajat(getAngajat(),actualHash);
        } catch (
                NoSuchAlgorithmException e) {
        }
    }
    private void logout(){

        service.logoutAngajat(angajat);
        primaryStage.close();
    }
    @FXML
    public void Logout(ActionEvent actionEvent) {
       logout();

    }
}
