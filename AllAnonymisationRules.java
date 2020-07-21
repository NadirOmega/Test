package anonymisation;

import java.util.Random;

/**
 * Class relative to Anonymisation rules. Here is all method use to verify a
 * given string
 * 
 * @version 1.0
 * @author Nadir Omega
 * @author ylgn
 */
public class AllAnonymisationRules {

	private static final String alphabet = "abcdefghijklmnopqrstuvwxy";

	/**
	 * Return an e-mail encrypted with all char before the @
	 * 
	 * @param String giving the e-mail
	 * @return String that contains the anonymised partial address
	 */
	public static String anonymiseForLocalPart(String str) {
		String anonymisedString = "";
		Random r = new Random();
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("@")) {
				while (i < str.length()) {
					anonymisedString += str.charAt(i);
					i++;
				}
				break;
			}
			anonymisedString += alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return anonymisedString;
	}

	/**
	 * Return a String encrypted entirely
	 * 
	 * @param String giving the data to encrypt
	 * @return String that contains the anonymised string
	 */
	public static String anonymiseFull(String str) {
		String anonymisedString = "";
		Random r = new Random();
		for (int i = 0; i < str.length(); i++) {
			anonymisedString += alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return anonymisedString;
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	
	public static String cryptAes(String string) {
		final String secretKey = "MayaAdjaz95";
	    String encryptedString = Crypt.encrypt(string, secretKey) ;
		return encryptedString;
	}
}
