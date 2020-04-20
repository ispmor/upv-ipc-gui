/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import static com.sun.glass.ui.Cursor.setVisible;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Booking;
import paddleexperience.CustomExceptions.SignupException;





/**
 *
 * @author Chris
 */
public class ReservarController implements Initializable {

    @FXML
   private DatePicker datePicker;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private TableColumn columnDisponibilidad;
    
    @FXML
    private TableColumn columnHorarioStart;
    
    @FXML
    private TableColumn columnHorarioEnd;
    
  
    
   private BackendFunctionality backend; 
   
   private String lastCourt;
   private Booking selectedBooking;
   
   
   @FXML
   private void reservar(ActionEvent event) throws IOException {
       boolean success = false;
       if(selectedBooking != null && lastCourt != null && datePicker.getValue() != null){
           System.out.println(lastCourt);
           success = backend.addNewBooking(lastCourt
                   , datePicker.getValue()
                   , selectedBooking.getFromTime());
       } 
       if(!success ){
           System.out.println("Failed to create a booking");
       }
       updateCourtTableViewOnClick(event);
   }
   
   @FXML
   private void select(MouseEvent event){
       this.selectedBooking = ((Booking) tableView.getSelectionModel().getSelectedItem());
       System.out.println(this.selectedBooking);
   }
   
   @FXML
   private void updateDate(ActionEvent event){
        LocalDate date = datePicker.getValue();
        if(date == null){
            date = LocalDate.now();
        }
        if(lastCourt != null){
            updateCourtTableViewForDateAndCourt(date, lastCourt);
        }
   }
   
   @FXML
    private void updateCourtTableViewOnClick(ActionEvent event){
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
            
            default:
                courtName = this.lastCourt;
        }
        this.lastCourt = courtName;
        LocalDate date = datePicker.getValue();
        if(date == null){
            date = LocalDate.now();
            datePicker.setValue(date);
        }
        updateCourtTableViewForDateAndCourt(date, courtName);
    }
    
    private void updateCourtTableViewForDateAndCourt(LocalDate date, String court){
        tableView.setItems(backend.getCourtForDateBooking(court, date));
        columnHorarioStart.setCellValueFactory(new PropertyValueFactory<Booking, String>("fromTime"));
        columnHorarioEnd.setCellFactory(new Callback<TableColumn<Booking, String>,
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
                                    
                                    LocalTime time = param.getTableView()
                                            .getItems()
                                            .get(currentIndex)
                                            .getFromTime()
                                            .plusMinutes(
                                             backend.getDuration()
                                            );
                                    
                                        setText(time.toString());
                                    
                                } 
                            }
                        };
                    }
                });
        
        columnDisponibilidad.setCellFactory(new Callback<TableColumn<Booking, String>,
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
                                    
                                    boolean status = param.getTableView()
                                            .getItems()
                                            .get(currentIndex)
                                            .getPaid();
                                    
                                    if(status){
                                        setTextFill(Color.WHITE);
                                        setStyle("-fx-font-weight: bold");
                                        setStyle("-fx-background-color: red");
                                        setText("Reservado");
                                    } else {
                                        setTextFill(Color.WHITE);
                                        setStyle("-fx-font-weight: bold");
                                        setStyle("-fx-background-color: green");
                                        setText("Disponible");
                                    }
                                    
                                } 
                            }
                        };
                    }
                });
        System.out.println(tableView.getItems());
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
