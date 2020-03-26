/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author barpus
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    private ClubDBAccess clubDB;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText(clubDB.getClubName());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clubDB = ClubDBAccess.getSingletonClubDBAccess();
        
    }    
    
}
