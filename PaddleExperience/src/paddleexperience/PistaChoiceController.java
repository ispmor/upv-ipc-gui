/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;





/**
 *
 * @author Chris
 */
public class PistaChoiceController implements Initializable {
    
  
    
   private BackendFunctionality backend; 
   
  
    
    @FXML
    private void reserva(ActionEvent event) throws IOException {
        
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReservarView.fxml"));
        stage.setTitle("Paddle Experience APP - Reservar pista");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();
    }
   
    
    @FXML
    private void ver(ActionEvent event) throws IOException {
        
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VerView.fxml"));
        stage.setTitle("Paddle Experience APP - Mis pistas");
        stage.setScene(new Scene(root));
        stage.show();
     
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();
    }
     
    @FXML
    private void GoBack(ActionEvent event) throws IOException {
        
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
        stage.setTitle("Paddle Experience APP - Mis pistas");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();
        
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = new BackendFunctionality();
    }

}
