import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
    static String sign(String secret, long ts, String payload) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] raw = mac.doFinal((ts + "." + payload).getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : raw) sb.append(String.format("%02x", b));
        return sb.toString();
    }
    static boolean verify(String secret, long ts, long now, long tolSec, String payload, String sig) throws Exception {
        if (Math.abs(now - ts) > tolSec) return false;
        return MessageDigest.isEqual(sign(secret, ts, payload).getBytes(), sig.getBytes());
    }
    public static void main(String[] args) throws Exception {
        String sig = sign("whsec_java", 1000, "{\"event\":\"paid\"}");
        if (!verify("whsec_java", 1000, 1020, 60, "{\"event\":\"paid\"}", sig)) throw new AssertionError("Valid sig failed");
        if (verify("whsec_java", 1000, 1200, 60, "{\"event\":\"paid\"}", sig)) throw new AssertionError("Replay not blocked");
        System.out.println("PASS 03-webhook-hmac-verifier");
    }
}
