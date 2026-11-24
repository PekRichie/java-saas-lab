import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Main {
    static <T> T runWithBackoff(Supplier<T> fn, int maxAttempts, long baseMs, List<Long> delaysLog) {
        RuntimeException last = null;
        for (int i = 0; i < maxAttempts; i++) {
            try { return fn.get(); }
            catch (RuntimeException e) {
                last = e;
                if (i + 1 < maxAttempts) delaysLog.add(baseMs * (1L << i));
            }
        }
        throw last;
    }
    public static void main(String[] args) {
        AtomicInteger n = new AtomicInteger();
        List<Long> delays = new ArrayList<>();
        String res = runWithBackoff(() -> {
            if (n.incrementAndGet() < 3) throw new RuntimeException("503");
            return "200 OK";
        }, 4, 100, delays);
        if (!"200 OK".equals(res) || !delays.equals(List.of(100L, 200L)))
            throw new AssertionError("Backoff failed: " + delays);
        System.out.println("PASS 24-retry-backoff");
    }
}
