import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    record ProrationResult(BigDecimal netDelta, BigDecimal vat, BigDecimal grossDelta) {}
    static ProrationResult calc(BigDecimal oldNet, BigDecimal newNet, int daysLeft, int cycleDays) {
        BigDecimal ratio = BigDecimal.valueOf(daysLeft).divide(BigDecimal.valueOf(cycleDays), 6, RoundingMode.HALF_UP);
        BigDecimal delta = newNet.subtract(oldNet).multiply(ratio).setScale(0, RoundingMode.HALF_UP);
        BigDecimal vat = delta.multiply(new BigDecimal("0.27")).setScale(0, RoundingMode.HALF_UP);
        return new ProrationResult(delta, vat, delta.add(vat));
    }
    public static void main(String[] args) {
        ProrationResult r = calc(new BigDecimal("10000"), new BigDecimal("30000"), 15, 30);
        if (!r.netDelta().equals(new BigDecimal("10000")) || !r.grossDelta().equals(new BigDecimal("12700")))
            throw new AssertionError("Wrong proration: " + r);
        System.out.println("PASS 04-subscription-proration");
    }
}
