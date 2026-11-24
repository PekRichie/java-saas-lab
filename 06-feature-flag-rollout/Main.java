import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class Main {
    record Flag(boolean enabled, int percent, Set<String> allowList) {}
    static int bucket(String key, String tenant) throws Exception {
        byte[] d = MessageDigest.getInstance("SHA-256").digest((key + ":" + tenant).getBytes(StandardCharsets.UTF_8));
        int v = ((d[0] & 0xFF) << 8) | (d[1] & 0xFF);
        return v % 100;
    }
    static boolean isEnabled(Flag f, String key, String tenant) throws Exception {
        if (!f.enabled()) return false;
        if (f.allowList().contains(tenant)) return true;
        return bucket(key, tenant) < f.percent();
    }
    public static void main(String[] args) throws Exception {
        Flag f = new Flag(true, 25, Set.of("vip"));
        if (!isEnabled(f, "ai_v2", "vip")) throw new AssertionError("VIP must be enabled");
        if (isEnabled(f, "ai_v2", "t42") != isEnabled(f, "ai_v2", "t42")) throw new AssertionError("Must be deterministic");
        System.out.println("PASS 06-feature-flag-rollout");
    }
}
