/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import model.Booking;
import model.Court;
import model.Member;
import paddleexperience.CustomExceptions.SignupException;

/**
 *
 * @author barpus
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    private ClubDBAccess clubDB;
    private Member member;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText(clubDB.getClubName());
        try {
            signUp("Bartosz", "Puszkarski", "12312312312", "Login with blank", "PasswordWithB@@@@D Chars", "", "", null);
        } catch (SignupException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.clubDB = ClubDBAccess.getSingletonClubDBAccess();

    }
    
    private boolean addNewBooking(String courtName, LocalDate date, LocalTime fromTime){
        ArrayList<Booking> allBookings = clubDB.getBookings();
        Court court = clubDB.getCourt(courtName);
        int bookingDuration = clubDB.getClubBookingDuration();
        boolean validNewRequest = allBookings.stream().anyMatch((Booking booking) -> {
           return !(booking.getMadeForDay().compareTo(date) == 0 &&
                   (booking.getFromTime().compareTo(fromTime) <= 0 &&
                        booking.getFromTime().plusMinutes(bookingDuration).compareTo(fromTime) > 0) &&
                   booking.getCourt().getName().equals(courtName));});
        
        if(validNewRequest){
            allBookings.add(new Booking(LocalDateTime.now(), date, fromTime, true, court, member));
        }
        return false;
    }
    
    private ObservableList<Booking> getBookingForDate(LocalDate date){
        ObservableList<Booking> result = FXCollections.observableList(clubDB.getForDayBookings(date));
        return result;
    }
    
    private ObservableList<Booking> getCourtForDateBooking(String courtName, LocalDate date){
        ObservableList<Booking> result = FXCollections.observableList(clubDB.getCourtBookings(courtName, date));
        return result;
    }
    
    private boolean login(String login, String password){
        Member loggedMember = clubDB.getMemberByCredentials(login, password);
        if(loggedMember == null){
            return false;
        } 
        this.member = loggedMember;
        return true;
    }

    private boolean signUp(String name, String surname, String phone, String login, String password, String creditCard, String svc, Image image) throws SignupException {

        ArrayList<String> errors = new ArrayList<>();

        if (name.isEmpty()) {
            errors.add("Invalid Name");
        }
        if (surname.isEmpty()) {
            errors.add("Invalid Surname");
        }
        if (phone.isEmpty()) {
            errors.add("Invalid Phone Number");
        }
        if (!validLogin(login)) {
            errors.add("Invalid Login");
        }
        if (!validPassword(password)) {
            errors.add("Invalid Password");
        }
        if (!validCreditCard(creditCard, svc)) {
            errors.add("Invalid Credit Card Number or SVC");
        }

        if (errors.isEmpty()) {
            clubDB.getMembers().add(new Member(name, surname, phone, login, password, creditCard, svc, image));
            return true;
        }
        throw new SignupException(String.join(", ", errors));
    }

    private boolean validLogin(String login) {
        return !login.contains(" ") && !clubDB.existsLogin(login);
    }

    private boolean validPassword(String password) {
        return password.length() > 6 && password.chars().allMatch(Character::isLetterOrDigit);
    }

    private boolean validCreditCard(String creditCard, String svc) {
        return (creditCard.isEmpty() && svc.isEmpty())
                || (creditCard.chars().allMatch(Character::isDigit)
                && svc.chars().allMatch(Character::isDigit)
                && creditCard.length() == 16
                && svc.length() == 3);
    }
}
