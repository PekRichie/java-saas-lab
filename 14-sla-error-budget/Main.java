public class Main {
    record Report(double achieved, double allowedMin, boolean compliant) {}
    static Report compute(double downtimeMin, double targetSla, int days) {
        double total = days * 24.0 * 60.0;
        double allowed = total * (1.0 - targetSla / 100.0);
        double achieved = (1.0 - downtimeMin / total) * 100.0;
        return new Report(achieved, allowed, achieved >= targetSla);
    }
    public static void main(String[] args) {
        if (!compute(15, 99.9, 30).compliant() || compute(60, 99.9, 30).compliant())
            throw new AssertionError("SLA calculation error");
        System.out.println("PASS 14-sla-error-budget");
    }
}
