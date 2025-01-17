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
public class PaddleExperienceController implements Initializable {
    
    
    @FXML
    private void court(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PistaView.fxml"));
        stage.setTitle("Paddle Experience APP - Ver disponibilidad de pistas");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
         actual.close();
    }
    
    @FXML
    private void reg(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("RegisterView.fxml"));
        stage.setTitle("Paddle Experience APP - Registro de socio");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
         actual.close();
    }
    
    @FXML
    private void aut(ActionEvent event) throws IOException {
        
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        stage.setTitle("Paddle Experience APP - Autentificarse");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
         actual.close();
     
    }
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
   
    }
    
    
    
        
         
  
    
}
