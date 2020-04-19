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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Booking;
import model.Court;





/**
 *
 * @author Chris
 */
public class PistasController implements Initializable {
    
  
    
   private BackendFunctionality backend; 
   
    @FXML
    private TableView tableView;
    
    @FXML 
    private TableColumn<Booking, String> columnHorarios;
    
    @FXML
    private void GoBack(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PaddleExperienceView.fxml"));
        stage.setTitle("Paddle Experience APP - Menú principal");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage actual  = (Stage) ((Node) event.getSource()).getScene().getWindow();
            actual.close();
        
    }
    
    @FXML
    private void updateCourtTableView(ActionEvent event){
        String courtName = ((Button)event.getSource()).getText();
        
        switch(courtName){
            case "Piso 1":
                courtName = "Court 1";
                break;
            case "Piso 2":
                courtName = "Court 2";
                break;
            case "Piso 3":
                courtName = "Court 3";
                break;
            case "Piso 4":
                courtName = "Court 4";
                break;
                
        }
        tableView.setItems(backend.getCourtForDateBooking(courtName, LocalDate.now()));
        columnHorarios.setCellValueFactory(new PropertyValueFactory<Booking, String>("fromTime"));
        System.out.println(tableView.getItems());
    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();
    }

}
