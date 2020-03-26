/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience.CustomExceptions;

/**
 *
 * @author barpus
 */
public class SignupException extends Exception{
    public SignupException(String message){
        super( "Failed to create new account. Reason: " + message);       
    }
    
    /**
     * If you need to get the messege -> exception.getMessage() will return you 
     * all you need to print out
     */
}
