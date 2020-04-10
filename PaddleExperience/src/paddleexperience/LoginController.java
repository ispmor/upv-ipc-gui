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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        if(backend.login(textLogin.getText(), textPassword.getText())){
            System.out.println("Logged successfully");
            //next stage + logged member
        } else {
            // Error message handling
        }
        
        
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        // bakc to paddleexample page
              
    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = new BackendFunctionality();        
    }

}
