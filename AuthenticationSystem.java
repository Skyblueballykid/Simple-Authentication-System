/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class AuthenticationSystem {
     public static void main(String[] args) throws Exception {
        
        Scanner scnr = new Scanner(System.in);
        FileInputStream admin = null; //file input streams
        FileInputStream zookeeper = null;
        FileInputStream veterinarian = null;
        
        
        //create array based on credentials.txt
        final int NUM_ELEMENTS = 6;
        String[] storedUsername = new String[NUM_ELEMENTS];
        String[] role = new String[NUM_ELEMENTS];
        String[] hashPassword = new String[NUM_ELEMENTS];
        String username = "";
        String password = "";
        String hash = "";
        userRoles userInput = new userRoles();
        String userRole = "";
        int i = 0; //loop variable
        int j = 0; //loop variable
        int user = 0;
        boolean verified = false;
        
        //populate username/password array
        storedUsername[i] = "griffin.keyes";      	hashPassword[i] = "108de81c31bf9c622f76876b74e9285f"; ++i;
        storedUsername[i] = "rosario.dawson";       hashPassword[i] = "3e34baa4ee2ff767af8c120a496742b5"; ++i;
        storedUsername[i] = "bernie.gorilla";      	hashPassword[i] = "a584efafa8f9ea7fe5cf18442f32b07b"; ++i;
        storedUsername[i] = "donald.monkey";      	hashPassword[i] = "17b1b7d8a706696ed220bc414f729ad3"; ++i;
        storedUsername[i] = "jerome.grizzlybear";  	hashPassword[i] = "3adea92111e6307f8f2aae4721e77900"; ++i;
        storedUsername[i] = "bruce.grizzlybear"; 	hashPassword[i] = "0d107d09f5bbe40cade3de5c71e9e9b7"; ++i;
        
        //display usage information for user
        JOptionPane.showMessageDialog(null, "All entries are case sensitive.\n" + 
                "Enter \"quit\" at any time to exit.", "Authentication System", JOptionPane.PLAIN_MESSAGE);
        
            //prompt user for credentials
            username = (String)JOptionPane.showInputDialog(null, "Enter username: ", "Authentication System", JOptionPane.PLAIN_MESSAGE); 
                if (username.equals("quit")){
                    JOptionPane.showMessageDialog(null, "You have chosen to exit. Goodbye.", "Authentication System", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            password = (String)JOptionPane.showInputDialog(null, "Enter password: ", "Authentication System", JOptionPane.PLAIN_MESSAGE);
                if (password.equals("quit")){
                    JOptionPane.showMessageDialog(null, "You have chosen to exit. Goodbye.", "Authentication System", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
            //check the validation of the user no more then 3 times.
            for (i = 0; i < 3; ++i){
                
                //compare user credentials to storedUsername and convert password
                for (j = 0; j < NUM_ELEMENTS; ++j) {        
                    if (username.equals(storedUsername[j])){
                        String original = password;  //provided MD5Digest code
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(original.getBytes());
                        byte[] digest = md.digest();
                        StringBuffer sb = new StringBuffer();
                        for (byte b : digest) {
                            sb.append(String.format("%02x", b & 0xff));
                        }
                        
                        verified = true;
                        user = j;
                        hash = sb.toString();
                    }
                }
                        //if there are invalid credentials prompt for re-entry
                        if (!hash.equals(hashPassword[user])) {
                            verified = false;
                            if (i<2) {
                                JOptionPane.showMessageDialog(null, "Invalid user credentials. " + (2-i) 
                                        + " attempt(s) remaining.", "Authentication System", JOptionPane.PLAIN_MESSAGE);
                                username = (String)JOptionPane.showInputDialog(null, "Enter username: ", "Authentication System",
                                        JOptionPane.PLAIN_MESSAGE); 
                                    if (username.equals("quit")){
                                        JOptionPane.showMessageDialog(null, "You have chosen to exit. Goodbye.", "Authentication System",
                                                JOptionPane.WARNING_MESSAGE);
                                        break;
                                    }
                                password = (String)JOptionPane.showInputDialog(null, "Enter password: ", "Authentication System",
                                        JOptionPane.PLAIN_MESSAGE);
                                    if (password.equals("quit")){
                                        JOptionPane.showMessageDialog(null, "You have chosen to exit. Goodbye.", "Authentication System",
                                                JOptionPane.WARNING_MESSAGE);
                                        break;
                                    }
                            }
                            //give error message if incorrect password on final attempt
                            if((!verified) && (i==2)) {
                                JOptionPane.showMessageDialog(null, "Could not verify credentials. Goodbye.", "Authentication System",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        
                        //user credentials verified
                        else {
                            userInput.setUser(username); //set username
                            userRole = userInput.getRole(); //find corresponding role for user
                            userInput.printWelcome(); //display welcome message to user
        
                            return;
                                
                        }
            }
     }
}