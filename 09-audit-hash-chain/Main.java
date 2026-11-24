import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class Main {
    static class Entry {
        int idx; String actor, action, prev, hash;
        Entry(int idx, String actor, String action, String prev, String hash) {
            this.idx=idx; this.actor=actor; this.action=action; this.prev=prev; this.hash=hash;
        }
    }
    static String sha256(String s) throws Exception {
        byte[] b = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }
    static boolean verify(List<Entry> list) throws Exception {
        String prev = "0".repeat(64);
        for (int i = 0; i < list.size(); i++) {
            Entry e = list.get(i);
            if (e.idx != i || !e.prev.equals(prev)) return false;
            if (!e.hash.equals(sha256(e.idx + "|" + e.actor + "|" + e.action + "|" + e.prev))) return false;
            prev = e.hash;
        }
        return true;
    }
    public static void main(String[] args) throws Exception {
        List<Entry> log = new ArrayList<>();
        String p0 = "0".repeat(64);
        String h0 = sha256("0|admin|tenant.create|" + p0);
        log.add(new Entry(0, "admin", "tenant.create", p0, h0));
        String h1 = sha256("1|u1|invoice.export|" + h0);
        log.add(new Entry(1, "u1", "invoice.export", h0, h1));
        if (!verify(log)) throw new AssertionError("Should verify");
        log.get(0).action = "tampered";
        if (verify(log)) throw new AssertionError("Tamper not detected");
        System.out.println("PASS 09-audit-hash-chain");
    }
}
