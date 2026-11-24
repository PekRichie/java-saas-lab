public class Main {
    static int estimateTokens(String text) { return Math.max(1, (text.length() + 3) / 4); }
    static double estimateUsd(String prompt, int outTokens, double inPer1M, double outPer1M) {
        return (estimateTokens(prompt) / 1_000_000.0) * inPer1M + (outTokens / 1_000_000.0) * outPer1M;
    }
    public static void main(String[] args) {
        String p = "Összegezd a következő SaaS projektet: MAGMA platform";
        double usd = estimateUsd(p, 500, 0.15, 0.60);
        if (usd <= 0.0) throw new AssertionError("Cost must be positive");
        System.out.println("PASS 20-ai-prompt-cost-estimator");
    }
}
