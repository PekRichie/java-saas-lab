import java.math.BigDecimal;
import java.util.*;

public class Main {
    record Line(String name, int qty, BigDecimal unitNet) { BigDecimal net() { return unitNet.multiply(BigDecimal.valueOf(qty)); } }
    public static void main(String[] args) {
        List<Line> lines = List.of(new Line("MAGMA Pro", 2, new BigDecimal("15000")), new Line("AI Add-on", 1, new BigDecimal("10000")));
        BigDecimal net = lines.stream().map(Line::net).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal gross = net.multiply(new BigDecimal("1.27"));
        if (net.compareTo(new BigDecimal("40000")) != 0 || gross.compareTo(new BigDecimal("50800.00")) != 0)
            throw new AssertionError("Invoice math error");
        System.out.println("PASS 19-invoice-text-generator");
    }
}
