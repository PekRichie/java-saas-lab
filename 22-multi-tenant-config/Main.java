import java.util.*;

public class Main {
    static Map<String, Object> resolve(Map<String, Object> def, Map<String, Object> plan, Map<String, Object> tenant) {
        Map<String, Object> out = new HashMap<>(def);
        if (plan != null) out.putAll(plan);
        if (tenant != null) out.putAll(tenant);
        return out;
    }
    public static void main(String[] args) {
        Map<String, Object> cfg = resolve(Map.of("seats", 5, "ai", false), Map.of("seats", 50, "ai", true), Map.of("seats", 250));
        if (!Integer.valueOf(250).equals(cfg.get("seats")) || !Boolean.TRUE.equals(cfg.get("ai")))
            throw new AssertionError("Config precedence failed");
        System.out.println("PASS 22-multi-tenant-config");
    }
}
