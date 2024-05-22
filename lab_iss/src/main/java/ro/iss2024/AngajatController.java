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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class AngajatController implements Observer {
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
        for (int i = 0; i <= 12; i++) {
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
//        Collection<Sarcina> msg = service.getAllSarcini();
//        model.setAll(msg);

    }

    @Override
    public void update() {
        initModel();
    }

    private Angajat getAngajat() {
        return this.angajat;
    }

    @FXML
    private void handleInregsitrarePrezent(ActionEvent event) {
        String oras = ora.getSelectionModel().getSelectedItem() + ":" + minute.getSelectionModel().getSelectedItem() + ":" +
                sec.getSelectionModel().getSelectedItem();

        service.updateAngajat(getAngajat(), Time.valueOf(oras));
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
}
