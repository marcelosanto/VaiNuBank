package banco.seguranca;

import java.security.MessageDigest;

public class Seguranca {
    public static String gerarHash(String senha) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(senha.getBytes());

            byte[] result = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
