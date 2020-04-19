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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Booking;
import model.Court;
import model.Member;





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
    private TableColumn<Booking, String> participant;
    
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        
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
            case "Pista 1":
                courtName = "Court 1";
                break;
            case "Pista 2":
                courtName = "Court 2";
                break;
            case "Pista 3":
                courtName = "Court 3";
                break;
            case "Pista 4":
                courtName = "Court 4";
                break;
                
        }
        tableView.setItems(backend.getCourtForDateBooking(courtName, LocalDate.now()));
        columnHorarios.setCellValueFactory(new PropertyValueFactory<Booking, String>("fromTime"));
        participant.setCellFactory(new Callback<TableColumn<Booking, String>,
                TableCell<Booking, String>>(){
                    @Override
                    public TableCell<Booking, String> call(
                    TableColumn<Booking, String> param) {
                        return new TableCell<Booking, String>(){
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                if(!empty){
                                    int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                                    
                                    Member member = param.getTableView()
                                            .getItems()
                                            .get(currentIndex)
                                            .getMember();
                                    if(member != null){
                                        setText(member.getLogin());
                                    }
                                } 
                            }
                        };
                    }
                });
        System.out.println(tableView.getItems());
    }
   
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();
    }

}
