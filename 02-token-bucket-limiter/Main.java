import java.util.*;

public class Main {
    static class Bucket { double tokens; long lastMs; Bucket(double t, long m){tokens=t;lastMs=m;} }
    static class RateLimiter {
        private final double cap, refillPerSec;
        private final Map<String, Bucket> map = new HashMap<>();
        RateLimiter(double cap, double refillPerSec) { this.cap = cap; this.refillPerSec = refillPerSec; }
        boolean allow(String tenant, long nowMs) {
            Bucket b = map.computeIfAbsent(tenant, k -> new Bucket(cap, nowMs));
            double elapsed = Math.max(0, (nowMs - b.lastMs) / 1000.0);
            b.tokens = Math.min(cap, b.tokens + elapsed * refillPerSec);
            b.lastMs = nowMs;
            if (b.tokens >= 1.0) { b.tokens -= 1.0; return true; }
            return false;
        }
    }
    public static void main(String[] args) {
        RateLimiter rl = new RateLimiter(2, 1.0);
        if (!rl.allow("t1", 0) || !rl.allow("t1", 0) || rl.allow("t1", 0)) throw new AssertionError("Limit failed");
        if (!rl.allow("t1", 1000)) throw new AssertionError("Refill failed");
        System.out.println("PASS 02-token-bucket-limiter");
    }
}
