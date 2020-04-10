/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
   
    
   private BackendFunctionality backend; 
   
   @FXML
   private void confirm(ActionEvent event) throws IOException {
        
       try {
           backend.signUp(textName.getText(), textSurname1.getText() + " " + textSurname2.getText(),
                   textPhone.getText(), textLogin.getText(), textPassword.getText(), textCreditCard.getText(), textSCV.getText(), null);
           System.out.println("Registered successfully");
       } catch (SignupException ex) {
           // how do we want to handle that? 
           System.out.println("Failed to register new user.");
           System.out.println(ex.getMessage());
           //switch to login scene with appropriate error?
       }
       
        
        
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {

    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = new BackendFunctionality();
    }

}
