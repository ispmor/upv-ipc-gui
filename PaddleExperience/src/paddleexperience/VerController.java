/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import paddleexperience.CustomExceptions.SignupException;
import model.LocalDateAdapter;
import model.DateTimeAdapter;
import model.LocalTimeAdapter;
import model.Booking;
import model.Court;




/**
 *
 * @author Chris
 */
public class VerController implements Initializable {   
   @FXML
   private TableView table;
   
   @FXML
   private TableColumn columnStart;
   
   @FXML 
   private TableColumn columnEnd;
   
   @FXML
   private TableColumn columnCourt;
   
   @FXML
   private TableColumn columnPaid;
   
   @FXML
   private TableColumn columnDia;
   
   private BackendFunctionality backend; 
   
   private Booking selectedBooking;

   
   @FXML
   private void delete(ActionEvent event) throws IOException {
       if(!backend.deleteBooking(selectedBooking)){
           throw new IOException("Booking already paid or less than 24h");
        }
       updateMemberBooking();
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
   
   @FXML
   private void select(MouseEvent event){
       this.selectedBooking = ((Booking) table.getSelectionModel().getSelectedItem());
       System.out.println(this.selectedBooking);
   }
   
   @FXML
   private void updateMemberBooking() {
       ObservableList obsList = backend.getUserBookings(backend.getMember().getLogin());
       int listsize = obsList.size();
       if(listsize > 10){
           obsList = (ObservableList) obsList.subList(listsize - 10, listsize -1 );
       }
       
       table.setItems(obsList);
       columnDia.setCellFactory(new Callback<TableColumn<Booking, String>,
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
                                    
                                    LocalDate day = param.getTableView()
                                            .getItems()
                                            .get(currentIndex)
                                            .getMadeForDay();
                                    
                                        setText(day.toString());
                                    
                                } 
                            }
                        };
                    }
                });
       
       columnStart.setCellValueFactory(new PropertyValueFactory<Booking, String>("fromTime"));
       columnCourt.setCellFactory(new Callback<TableColumn<Booking, String>,
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
                                    
                                    Court court = param.getTableView()
                                            .getItems()
                                            .get(currentIndex)
                                            .getCourt();
                                    
                                        setText(court.getName());
                                    
                                } 
                            }
                        };
                    }
                });
       columnEnd.setCellFactory(new Callback<TableColumn<Booking, String>,
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
        
        columnPaid.setCellFactory(new Callback<TableColumn<Booking, String>,
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
                                        setStyle("-fx-background-color: green");
                                        setText("Pagado");
                                    } else {
                                        setTextFill(Color.BLACK);
                                        setStyle("-fx-font-weight: bold");
                                        setStyle("-fx-background-color: grey");
                                        setText("Pendiente");
                                    }
                                    
                                } 
                            }
                        };
                    }
                });
   }
    
     
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backend = BackendFunctionality.getInstance();

        updateMemberBooking();
    }
}
        
    


