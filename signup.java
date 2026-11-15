
package Pages;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class signup {

    public static String hashPassword(String password)
            throws Exception {

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(
            password.toCharArray(),
            salt,
            600000,
            256
        );

        byte[] hash = SecretKeyFactory
            .getInstance("PBKDF2WithHmacSHA256")
            .generateSecret(spec)
            .getEncoded();

        spec.clearPassword();

        return Base64.getEncoder().encodeToString(salt)
            + ":"
            + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(
            String password, String storedPassword)
            throws Exception {

        String[] parts = storedPassword.split(":");

        byte[] salt = Base64.getDecoder()
            .decode(parts[0]);

        byte[] storedHash = Base64.getDecoder()
            .decode(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(
            password.toCharArray(),
            salt,
            600000,
            256
        );

        byte[] newHash = SecretKeyFactory
            .getInstance("PBKDF2WithHmacSHA256")
            .generateSecret(spec)
            .getEncoded();

        spec.clearPassword();

        return java.security.MessageDigest.isEqual(
            storedHash, newHash
        );
    }
}
