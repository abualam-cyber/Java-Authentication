/**
 * Southern New Hampshire University
 * IT - 145 Foundations in Application Development
 * Instructor: Joe Parker
 * Student: Abu S Alam
 * Date: 17 August 2019
 */
/**
 * Authentication System
 * Asks the user for username and password
 * The program then converts the user entered password to hash password
 * with the use of MD5 Digest, compares the hashed password with the one in the
 * credentials file, and if authorized, the user is logged in. The number of login 
 * attempts is limited to three in case of wrong username password. Once the user 
 * is authenticated, the program displays the corresponding file in accordance to 
 * the user's role. The program also allows the user to either logout of the system, 
 * or exit the program entirely, terminating the program.
 * 
 * @author Abu S Alam
 */

package authentication3;


import java.io.BufferedReader;
import java.security.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Authentication3 
  {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ArrayIndexOutOfBoundsException, Exception 
      {

        String uName = "";          //USERNAME FROM CREDENTIALS
        String hashPassword = "";   //HASH PW FROM CREDENTIALS
        String actualPassword = ""; // ACTUAL PW FROM CREDENTIALS
        String employeeRole = "";   // EMPLOYEE ROLE

        try {

            // *DISPLAY WELCOME MESSAGE AND OPTIONS*
            // DECLARE NEW SCANNER ORBJECT TO READ FROM USER
            Scanner scnr = new Scanner(System.in);

            //Scanner useDelimiter = scnr.useDelimiter("\n");
            boolean GO = true;
            int loginAttempt = 0;
            

            while (GO) {
                System.out.println("WELCOME!");
                System.out.println("Please enter 1 to login, 2 to exit");
                
                //parsing user input to read user input option, login or exit
                int opt = Integer.parseInt(scnr.nextLine().trim()); //parsing user input, 
                       
                if (opt == 1) {
                    loginAttempt++;     // INCREMENTING NUMBER OF LOGIN ATTEMPTS

                    // PROMPTING USER FOR USERNAME AND PASSWORD
                    System.out.println("Please enter username: ");
                    String userName = scnr.nextLine();
                    System.out.println("Please enter your password: ");
                    String passWord = scnr.nextLine();
                    
                    
                    HasPass h = new HasPass();  //GET HASH PW FROM HasPass
                    
                    String hashPW = h.hashPasswords(passWord); //COMPARE HASH PW TO ONE ON FILE

                    boolean wrongUser = true;
                    
                    //USING BUFFERREADER TO READ CREDENTIALS FILE
                    BufferedReader br = new BufferedReader(new FileReader("credentials.txt"));
                    String line = null;
                    
                    int count = 0;
                    
                    while ((line = br.readLine()) != null) {
                        String tmp[] = line.split("\t");
                        uName = tmp[0];
                        hashPassword = tmp[1];
                        actualPassword = tmp[2].replace("\"", "");
                        employeeRole = tmp[3].trim();
                            
                        count = count + 1;
                            
                            
                        //IF STATEMENT TO COMPARE USER ENTERED USERNAME AND PW TO CREDENTIALS FILE    
                        if (userName.contentEquals(uName) && hashPW.contentEquals(hashPassword)) {

                            System.out.println(employeeRole);
                            System.out.println("");
                            
                            if (hashPW.contentEquals(hashPassword)) {
                                // IF AUTHORIZED, LOGIN
                                List<String> data = null;
                                // REFERNCE USER TYPE AND PRINT ACCORDINGLY
                                switch (employeeRole) {
                                    case "zookeeper":
                                        data = Files.readAllLines(Paths.get("zookeeper.txt"), Charset.defaultCharset());
                                        break;
                                    case "veterinarian":
                                        data = Files.readAllLines(Paths.get("veterinarian.txt"), Charset.defaultCharset());
                                        break;
                                    case "admin":
                                        data = Files.readAllLines(Paths.get("admin.txt"), Charset.defaultCharset());
                                        break;
                                        
                                    case "consultant":
                                        data = Files.readAllLines(Paths.get("consultant.txt"), Charset.defaultCharset());
                                        break;
                                        
                                    case "security":
                                        data = Files.readAllLines(Paths.get("security.txt"), Charset.defaultCharset());
                                        break;

                                    default:
                                        break;
                                }
                                //PRINTING CONTENT OF FILE ONTO THE CONSOLE
                                for (String fileContent : data) 
                                  {
                                    System.out.println(fileContent);
                                  }
                                
                                //RESET LOGIN ATTEMPTS
                                loginAttempt = 0;

                                // PRINTING LOGOUT OPTION
                                System.out.println("");
                                System.out.println("Enter 1 to logout; 2 to exit!");
                                opt = Integer.parseInt(scnr.nextLine().trim());

                                //EXIT IF USER OPTION IS 2
                                if (opt == 2) 
                                  {
                                    GO = false;
                                  }
                                wrongUser = false;
                                break;
                            }

                        }
                            
                            
                        }
                        br.close(); // CLOSING CREDENTIALS FILE

                        

                    // IF USERNAME AND/OR PASSWORD INCORRECT, PROMPT FOR CREDENTIALS AGAIN
                    if (wrongUser) {
                        System.out.println("");
                        System.out.println("Username or password not valid");
                        System.out.println("");
                        System.out.println("Please enter correct username and password");
                        System.out.println("");
                      }
                } 
                else {
                    GO = false;
                    break;
                  }
                //LIMIT NUMBER OF LOGIN ATTEMPTS
                if (loginAttempt == 3) {
                    GO = false;
                    System.out.println("SORRY! Exceeded number of login attempts. GOODBYE!");
                    System.out.println("");
                  }
            }
        } 
        catch (IOException FIex) {
            FIex.printStackTrace();
          }
    }

}
