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
    private static BackendFunctionality instance = null;
    private ArrayList<Booking> allBookings;
    
    private BackendFunctionality(){
        this.clubDB = ClubDBAccess.getSingletonClubDBAccess();
        allBookings = clubDB.getBookings();
    }

    public static BackendFunctionality getInstance(){
        if(instance == null){
            instance = new BackendFunctionality();
        }
        
        return instance;
    }
    
    public boolean addNewBooking(String courtName, LocalDate date, LocalTime fromTime){
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
        save();
        return validNewRequest;
    }
    
    public ObservableList<Booking> getBookingForDate(LocalDate date){
        ArrayList<Booking> bookings = clubDB.getForDayBookings(date);
        ArrayList<Booking> freeDay = getFreeDay();
        if(!bookings.isEmpty()){
                int bookings_i = 0;
                for(int i = 0; i < freeDay.size(); i++){
                    if(freeDay.get(i).getFromTime() == bookings.get(bookings_i).getFromTime()){
                        freeDay.set(i, bookings.get(bookings_i));
                        bookings_i++; 
                    }
                }
        }
        return FXCollections.observableList(freeDay);
    }
    
    public ObservableList<Booking> getCourtForDateBooking(String courtName, LocalDate date){
        System.out.println(courtName);
        ArrayList<Booking> bookings = clubDB.getCourtBookings(courtName, date);
        System.out.println( bookings.size());
        ArrayList<Booking> freeDay = getFreeDay();
        if(!bookings.isEmpty()){
                int bookings_i = 0;
                for(int i = 0; i < freeDay.size(); i++){
                    if(freeDay.get(i).getFromTime().compareTo(bookings.get(bookings_i).getFromTime()) == 0){
                        freeDay.set(i, bookings.get(bookings_i));
                        bookings_i++; 
                        if(bookings_i == bookings.size()) {
                            break;
                        }
                    }
                }
        }
        return FXCollections.observableList(freeDay);
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
    
    public ArrayList<Booking> getUserBookingsArrayList(String login){
        return clubDB.getUserBookings(login);
    }
    
    public boolean deleteBooking(Booking booking){
        ArrayList<Booking> bookings = clubDB.getBookings();
        System.out.println(booking.getFromTime().plusHours(24).isAfter(LocalTime.now()));
        if(bookings.contains(booking) && 
                !booking.getFromTime().plusHours(24).isAfter(LocalTime.now()) &&
                 booking.getPaid()){
            bookings.remove(booking);
            save();
            return true;
        }
        return false;
    }
    
    public ArrayList<Booking> getFreeDay(){
        ArrayList<Booking> freeDay = new ArrayList<>();
        int minutes = 9 * 60;
        int minutes_from;
        int h, h_to, m, m_to;
        for(int i = 0; i < clubDB.getClubBookingSlots(); i++){
            minutes_from = minutes;
            minutes = minutes + clubDB.getClubBookingDuration();
            h = minutes_from / 60;
            m = minutes_from % 60;
            h_to = minutes / 60;
            m_to = minutes % 60;
            
            freeDay.add(new Booking(LocalDateTime.now(),
                    LocalDate.now(),
                    LocalTime.of(h, m),
                    false,
                    null, null));
        }
        return freeDay;
    }
    
    public Court getCourt(String courtName){
        return clubDB.getCourt(courtName);
    }
    
    public int getDuration(){
        return clubDB.getClubBookingDuration();
    }
    
    
    public Member getMember(){
        return member;
    }
    
    public String getClubName(){
        return clubDB.getClubName();
    }
    
    public void save(){
        clubDB.saveDB();
    }
}
