/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import paddleexperience.CustomExceptions.SignupException;
import model.LocalDateAdapter;
import model.DateTimeAdapter;
import model.LocalTimeAdapter;
import model.Booking;




/**
 *
 * @author Chris
 */
public class VerController implements Initializable {
    
   
   @FXML
   private DatePicker datePicker;
   
    
   private BackendFunctionality backend; 
   
   @FXML
   private void confirm(ActionEvent event) throws IOException {
       
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PistaChoiceView.fxml"));
        stage.setTitle("Paddle Experience APP - Menú de pistas");
        stage.setScene(new Scene(root));
        stage.show();
        
         Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
           actual.close();
       
   }
    
   @FXML
   private void cancel(ActionEvent event) throws IOException {
       
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PistaChoiceView.fxml"));
        stage.setTitle("Paddle Experience APP - Menú de pistas");
        stage.setScene(new Scene(root));
        stage.show();
        
         Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
           actual.close();

   }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();
        
        datePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });
    }
}
        
    


