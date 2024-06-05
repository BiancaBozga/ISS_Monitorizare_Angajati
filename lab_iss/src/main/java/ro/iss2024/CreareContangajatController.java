package ro.iss2024;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CreareContangajatController {
    @FXML
    TextField username;
    private Stage primaryStage;
    private Stage secondStage;
    Service service;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }
    public void setService(Service service1) {

        service = service1;
//        service.addObserver(this);

    }

    private static String generateRandomPassword() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/";

        int length = 7; // Lungimea parolei
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }
    @FXML
    public void Adauga(ActionEvent actionEvent) {



        if(username.getText()!=""){
            Angajat a=service.findAngajatByusername(username.getText());

            if(a!=null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Utilizator existent");
                alert.setHeaderText(null);
                alert.setContentText("Exisat deja un cont cu usernameul: "+username.getText());
                alert.showAndWait();
                return;
            }
            else{
                String passwd1 = generateRandomPassword();
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwd1.getBytes());
            byte[] digest = md.digest();
            String actualHash = bytesToHex(digest).toUpperCase();
            System.out.println(passwd1);
            System.out.println(actualHash);

            service.saveAngajat(new Angajat(username.getText(),actualHash));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Parola generata");
            alert.setHeaderText(null);
            alert.setContentText("Parola este :"+passwd1);
            alert.showAndWait();
            return;
        } catch (
                NoSuchAlgorithmException e) {
        }
    }}
    else{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Username gol");
        alert.setHeaderText(null);
        alert.setContentText("Username ul nu poate fi gol");
        alert.showAndWait();
        return;
    }
    }
}
