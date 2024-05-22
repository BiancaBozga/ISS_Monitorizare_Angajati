package ro.iss2024;




import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sef;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;



public class LogInController {
    Service service;

    @FXML
    private TextField user_id;
    @FXML
    private PasswordField passwd;
    private Stage primaryStage;
    private Stage secondStage;
    @FXML
    private Label warning;


    public void setService(Service service1) {

        service = service1;

    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }

    @FXML
    private void handleLogInButton(ActionEvent event) {

        if(passwd.getText()==""){
            Alert alerta1 = new Alert(Alert.AlertType.WARNING);
            alerta1.setTitle("Warning");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Parola  nu poate fi goala!");
            alerta1.showAndWait();
        }
        else if(user_id.getText()==""){
            Alert alerta1 = new Alert(Alert.AlertType.WARNING);
            alerta1.setTitle("Warning");
            alerta1.setHeaderText(null);
            alerta1.setContentText("Username nu poate fi gol!");
            alerta1.showAndWait();}
        else {
        try{


        // Hash the password
        String passwd1 = passwd.getText();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passwd1.getBytes());
        byte[] digest = md.digest();
        String actualHash = bytesToHex(digest).toUpperCase();
        System.out.println(passwd1);
        System.out.println(actualHash);

        // Check credentials and get the user object
        Object user = service.checkCredential(actualHash, user_id.getText());

        if (user instanceof Angajat) {
            // Load Angajat controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Angajat.fxml"));
            Parent root = loader.load();
            AngajatController angajatController = loader.getController();
            // Set necessary services and attributes
            angajatController.setService(service);
            // Create and show the new stage
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            angajatController.setPrimaryStage(newStage);
            angajatController.setAngajat((Angajat) user);
            newStage.show();

        } else if (user instanceof Sef) {
            // Load Sef controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sef.fxml"));
            Parent root = loader.load();
            SefController sefController = loader.getController();
            sefController.setService(service);
            // Create and show the new stage
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
           sefController.setPrimaryStage(newStage);
            newStage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Parola/Username invalida");
            alert.setHeaderText(null);

                alert.setContentText("Nu exista aceste credentiale");

            alert.showAndWait();
        }

    }catch(
                IOException e)

        {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch(
                NoSuchAlgorithmException e)

        {
            throw new RuntimeException(e);
        }}
    }




    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }



}
