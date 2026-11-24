import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Main {
    static class IdempotencyStore {
        private final Map<String, String> cache = new HashMap<>();
        String execute(String key, Supplier<String> fn) { return cache.computeIfAbsent(key, k -> fn.get()); }
    }
    public static void main(String[] args) {
        IdempotencyStore s = new IdempotencyStore();
        AtomicInteger calls = new AtomicInteger();
        String r1 = s.execute("pay-1", () -> "paid-" + calls.incrementAndGet());
        String r2 = s.execute("pay-1", () -> "paid-" + calls.incrementAndGet());
        if (!r1.equals(r2) || calls.get() != 1) throw new AssertionError("Not idempotent");
        System.out.println("PASS 12-idempotency-store");
    }
}
