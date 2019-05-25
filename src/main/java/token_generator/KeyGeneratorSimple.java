package token_generator;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

public class KeyGeneratorSimple implements KeyGenerator {

	private static String SECRET_KEY = "mySecretPasword1mySecretPasword2";
	
	@Override
    public Key generateKey() {
        
		try {
			SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8"));
			return key;
		} catch (WeakKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public static Key generateKeyStatic() {
		try {
			SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8"));
			return key;
		} catch (WeakKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}