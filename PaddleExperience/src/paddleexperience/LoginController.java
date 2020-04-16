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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;





/**
 *
 * @author Chris
 */
public class LoginController implements Initializable {
    
    @FXML 
    TextField textLogin;
    
    @FXML 
    TextField textPassword;
    
    private BackendFunctionality backend;
    
    @FXML
    private void confirm(ActionEvent event) throws IOException {
         
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        
        if(backend.login(textLogin.getText(), textPassword.getText())){
            System.out.println("Logged successfully"); 
            
            info.setTitle("Estado del inicio de sesión");
            info.setHeaderText("Logeado correctamente");
            
            info.showAndWait();
            
            //next stage + logged member
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("PistaChoiceView.fxml"));
            stage.setTitle("Paddle Experience APP - Ver disponibilidad de pistas");
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();
        } else {
            // Error message handling           
            error.setTitle(" Estado del inicio de sesión");
            error.setHeaderText(" Fallo en el inicio de sesión");
            error.setContentText("El usuario y contraseña no coinciden, inténtelo de nuevo con datos distintos");
            
            error.showAndWait();
        }
        
        
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        // back to paddleexample page
          Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
        stage.setTitle("Paddle Experience APP - Menú principal");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
         actual.close();

       
    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();        
    }

}
