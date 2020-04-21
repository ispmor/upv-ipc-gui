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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import paddleexperience.CustomExceptions.SignupException;





/**
 *
 * @author Chris
 */
public class RegisterController implements Initializable {
    
   @FXML
   private TextField textName;
   
   @FXML
   private TextField textSurname2;
   
   @FXML
   private TextField textPhone;
   
   @FXML
   private TextField textLogin;
   
   @FXML
   private TextField textPassword;
   
   @FXML
   private TextField textCreditCard;
   
   @FXML
   private TextField textSCV;
   
   @FXML
   private TextField textSurname1;
   
   @FXML
   private ImageView image;
   
    
   private BackendFunctionality backend; 
   
   @FXML
   private void confirm(ActionEvent event) throws IOException {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
       try {
           backend.signUp(textName.getText(), textSurname1.getText() + " " + textSurname2.getText(),
                   textPhone.getText(), textLogin.getText(), textPassword.getText(), textCreditCard.getText(), textSCV.getText(), image.getImage());
           System.out.println("Registered successfully");
           
           info.setTitle("Estado del registro");
           info.setHeaderText("Registrado correctamente");
           backend.save();
           info.showAndWait();
           
           Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
            stage.setTitle("Paddle Experience APP - Menú principal");
            stage.setScene(new Scene(root));
            stage.show();
         
           Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
           actual.close();
            
       } catch (SignupException ex) {
           // how do we want to handle that? 
           
           error.setTitle(" Estado del registro");
           error.setHeaderText(" Fallo en el registro");
           error.setContentText(ex.getMessage());
           
           System.out.println("Failed to register new user.");
           System.out.println(ex.getMessage());
           error.showAndWait();
           //switch to login scene with appropriate error?
           
       }
       
       
       
        
        
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {
         Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
         Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
        stage.setTitle("Paddle Experience APP - Menú principal");
        stage.setScene(new Scene(root));
        stage.show();
        
        actual.close();

    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();
    }

}
