package anonymisation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * DO UNIT TEST
 * @author Maya Adjaz
 *
 */
public class Crypt {

	 
	    private static SecretKeySpec MySecretKey;
	    
	 
	    public static void setTheSecretKey(String myKey) throws NoSuchAlgorithmException ,UnsupportedEncodingException
	    {
	    		byte[] tempKey;
	            tempKey = myKey.getBytes("UTF-8");
	    		MessageDigest  sha = MessageDigest.getInstance("SHA-1");
	            tempKey = sha.digest(tempKey);
	            tempKey = Arrays.copyOf(tempKey, 16); 
	            MySecretKey = new SecretKeySpec(tempKey, "AES");
	         
	
	    }
	 
	    public static String encrypt(String strToEncrypt, String secret) 
	    {
	        try
	        {
	            setTheSecretKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, MySecretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error Encrypt " + e.toString());
	        }
	        return null;
	    }
	 
	    public static String decrypt(String strToDecrypt, String secret) 
	    {
	        try
	        {
	            setTheSecretKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, MySecretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error  decrypt: " + e.toString());
	        }
	        return null;
	    }
	
	
}
