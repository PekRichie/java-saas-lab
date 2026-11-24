import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Long> usage = new HashMap<>();
        usage.merge("t1", 600L, Long::sum);
        usage.merge("t1", 700L, Long::sum);
        long billable = Math.max(0, usage.get("t1") - 1000L);
        BigDecimal fee = BigDecimal.valueOf(billable).multiply(new BigDecimal("2.5"));
        if (billable != 300 || fee.compareTo(new BigDecimal("750.0")) != 0) throw new AssertionError("Wrong billing");
        System.out.println("PASS 11-usage-metering");
    }
}
