/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import DBAcess.ClubDBAccess;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.Booking;
import model.Court;
import model.Member;
import paddleexperience.CustomExceptions.SignupException;
/**
 *
 * @author barpus
 */
public class BackendFunctionality {
    private ClubDBAccess clubDB;
    private Member member;
    
    public BackendFunctionality(){
        this.clubDB = ClubDBAccess.getSingletonClubDBAccess();
    }

    public BackendFunctionality(Member member){
        this.clubDB = ClubDBAccess.getSingletonClubDBAccess();
        this.member = member;
    }
    
    public boolean addNewBooking(String courtName, LocalDate date, LocalTime fromTime){
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
    
    public ObservableList<Booking> getBookingForDate(LocalDate date){
        ObservableList<Booking> result = FXCollections.observableList(clubDB.getForDayBookings(date));
        return result;
    }
    
    public ObservableList<Booking> getCourtForDateBooking(String courtName, LocalDate date){
        ObservableList<Booking> result = FXCollections.observableList(clubDB.getCourtBookings(courtName, date));
        return result;
    }
    
    public boolean login(String login, String password){
        Member loggedMember = clubDB.getMemberByCredentials(login, password);
        if(loggedMember == null){
            return false;
        } 
        this.member = loggedMember;
        return true;
    }

    public boolean signUp(String name, String surname, String phone, String login, String password, String creditCard, String svc, Image image) throws SignupException {

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

    public boolean validLogin(String login) {
        return !login.contains(" ") && !clubDB.existsLogin(login);
    }

    public boolean validPassword(String password) {
        return password.length() > 6 && password.chars().allMatch(Character::isLetterOrDigit);
    }

    public boolean validCreditCard(String creditCard, String svc) {
        return (creditCard.isEmpty() && svc.isEmpty())
                || (creditCard.chars().allMatch(Character::isDigit)
                && svc.chars().allMatch(Character::isDigit)
                && creditCard.length() == 16
                && svc.length() == 3);
    }

    public ObservableList<Booking> getUserBookings(String login){
        ObservableList<Booking> result = FXCollections.observableList(clubDB.getUserBookings(login));
        return result;
    }
    
    public boolean deleteBooking(Booking booking){
        ArrayList<Booking> bookings = clubDB.getBookings();
        if(bookings.contains(booking)){
            bookings.remove(booking);
            return true;
        }
        return false;
    }
    
    public Member getMember(){
        return member;
    }
}
