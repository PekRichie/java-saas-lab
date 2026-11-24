import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

public class Main {
    record KeyRecord(String id, String tenantId, String hash, Set<String> scopes, boolean revoked) {}

    static String sha256(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] b = md.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    static class ApiKeyManager {
        private final Map<String, KeyRecord> store = new HashMap<>();
        private final SecureRandom rng = new SecureRandom();

        String issue(String tenantId, Set<String> scopes) throws Exception {
            String id = Integer.toHexString(rng.nextInt(0x100000, 0xFFFFFF));
            String secret = Long.toHexString(rng.nextLong() & Long.MAX_VALUE);
            String raw = "cs_live_" + id + "." + secret;
            store.put(id, new KeyRecord(id, tenantId, sha256(raw), scopes, false));
            return raw;
        }

        boolean verify(String raw, String scope) throws Exception {
            if (!raw.startsWith("cs_live_") || !raw.contains(".")) return false;
            String id = raw.substring(8, raw.indexOf('.'));
            KeyRecord rec = store.get(id);
            if (rec == null || rec.revoked()) return false;
            return MessageDigest.isEqual(rec.hash().getBytes(), sha256(raw).getBytes()) && rec.scopes().contains(scope);
        }

        void revoke(String raw) {
            String id = raw.substring(8, raw.indexOf('.'));
            KeyRecord r = store.get(id);
            if (r != null) store.put(id, new KeyRecord(r.id(), r.tenantId(), r.hash(), r.scopes(), true));
        }
    }

    public static void main(String[] args) throws Exception {
        ApiKeyManager mgr = new ApiKeyManager();
        String key = mgr.issue("tenant-1", Set.of("read", "write"));
        if (!mgr.verify(key, "write")) throw new AssertionError("Expected valid write scope");
        if (mgr.verify(key, "admin")) throw new AssertionError("Expected denied admin scope");
        mgr.revoke(key);
        if (mgr.verify(key, "read")) throw new AssertionError("Expected revoked key");
        System.out.println("PASS 01-api-key-manager");
    }
}
