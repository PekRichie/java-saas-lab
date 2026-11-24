import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Main {
    static String b64(byte[] b) { return Base64.getUrlEncoder().withoutPadding().encodeToString(b); }
    static String hmac(String secret, String data) throws Exception {
        Mac m = Mac.getInstance("HmacSHA256");
        m.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return b64(m.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
    static String issue(String secret, String sub, long exp) throws Exception {
        String body = b64((sub + "|" + exp).getBytes(StandardCharsets.UTF_8));
        return body + "." + hmac(secret, body);
    }
    static String verify(String secret, String tok, long now) throws Exception {
        String[] p = tok.split("\\.");
        if (p.length != 2) return null;
        if (!MessageDigest.isEqual(hmac(secret, p[0]).getBytes(), p[1].getBytes())) return null;
        String[] fields = new String(Base64.getUrlDecoder().decode(p[0]), StandardCharsets.UTF_8).split("\\|");
        return now <= Long.parseLong(fields[1]) ? fields[0] : null;
    }
    public static void main(String[] args) throws Exception {
        String tok = issue("sec-key", "user-42", 2000);
        if (!"user-42".equals(verify("sec-key", tok, 1500)) || verify("sec-key", tok, 2500) != null)
            throw new AssertionError("Token check failed");
        System.out.println("PASS 21-signed-token");
    }
}
