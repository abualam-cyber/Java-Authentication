/**
 * The class HasPass is used only to generate hashed password from the 
 * password entered by user to compare to credentials file. The hashed password
 * is generated through the use of a MD5 digest.
 * 
 */
package authentication3;

import java.security.MessageDigest;

/**
 *
 * @author Abu S Alam
 */
public class HasPass {
    
    
    String hPass;
            
    public String hashPasswords(String passWord) throws Exception
      {
        String original = passWord;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest)
        {
            sb.append(String.format("%02x", b & 0xff));
        }
        
        System.out.println("");
        
        
        
        hPass=sb.toString();
        
        
        return hPass;
      }
}
