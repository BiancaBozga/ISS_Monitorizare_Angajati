package ro.iss2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.iss2024.business.Service;
import ro.iss2024.persistance.hibernate.AngajatRepository;
import ro.iss2024.persistance.hibernate.SarcinaRepository;
import ro.iss2024.persistance.hibernate.SefRepository;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class StartApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, NoSuchAlgorithmException {



        FXMLLoader userLoader = new FXMLLoader();
        userLoader.setLocation(getClass().getResource("/login.fxml"));
        AnchorPane userLayout = userLoader.load();
        stage.setScene(new Scene(userLayout));

        LogInController userController = userLoader.getController();
        AngajatRepository a_repo=new AngajatRepository();
        SarcinaRepository s_repo=new SarcinaRepository();
        SefRepository sef_repo=new SefRepository();
        Service service=new Service(s_repo,a_repo,sef_repo);

      userController.setService(service);


        // Create the second stage
        Stage secondStage = new Stage();

        // Set up the controller

        userController.setPrimaryStage(stage);
        userController.setSecondStage(secondStage);






        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}