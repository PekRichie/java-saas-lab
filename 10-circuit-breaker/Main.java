import java.util.function.Supplier;

public class Main {
    static class CircuitBreaker {
        private final int threshold; private final long recoveryMs;
        private int failures = 0; private String state = "CLOSED"; private long openedAt = 0;
        CircuitBreaker(int threshold, long recoveryMs) { this.threshold = threshold; this.recoveryMs = recoveryMs; }
        <T> T call(long nowMs, Supplier<T> fn) {
            if ("OPEN".equals(state)) {
                if (nowMs - openedAt >= recoveryMs) state = "HALF_OPEN";
                else throw new IllegalStateException("OPEN");
            }
            try { T r = fn.get(); failures = 0; state = "CLOSED"; return r; }
            catch (RuntimeException e) {
                if (++failures >= threshold) { state = "OPEN"; openedAt = nowMs; }
                throw e;
            }
        }
    }
    public static void main(String[] args) {
        CircuitBreaker cb = new CircuitBreaker(2, 5000);
        for (int i = 0; i < 2; i++) {
            try { cb.call(0, () -> { throw new RuntimeException("down"); }); } catch (RuntimeException ignored) {}
        }
        boolean blocked = false;
        try { cb.call(1000, () -> "ok"); } catch (IllegalStateException e) { blocked = true; }
        if (!blocked || !"ok".equals(cb.call(6000, () -> "ok"))) throw new AssertionError("Breaker failed");
        System.out.println("PASS 10-circuit-breaker");
    }
}
