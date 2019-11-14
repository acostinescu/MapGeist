package mapGeist;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Maptilities
{
	public static String encryptPassword(String password, String salt)
	{
		password = salt + password;
		String encryptedPassword = "";
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256"); 
			byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
			
			// Convert byte array into signum representation  
	        BigInteger number = new BigInteger(1, hash);  
	  
	        // Convert message digest into hex value  
	        StringBuilder hexString = new StringBuilder(number.toString(16));  
	  
	        // Pad with leading zeros 
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');
	        }  
	  
	        return encryptedPassword = hexString.toString();
		}
		catch(NoSuchAlgorithmException ex){}
		
		return encryptedPassword;
	}
}
