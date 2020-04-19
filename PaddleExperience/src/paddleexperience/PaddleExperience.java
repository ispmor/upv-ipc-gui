/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author barpus
 */
public class PaddleExperience extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
        primaryStage.setTitle("Paddle Experience APP - Menú principal");
        primaryStage.setScene(new Scene(root));
        
        
        primaryStage.setOnCloseRequest((WindowEvent event) ->{
        Alert alert = new Alert(AlertType.INFORMATION);
        BackendFunctionality backend = BackendFunctionality.getInstance();
        alert.setTitle(backend.getClubName());
        alert.setHeaderText("Saving data in DB");
        alert.setContentText("The application is saving the changes into the database. This action can expend some minutes.");
        alert.show();
        backend.save(); });
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
