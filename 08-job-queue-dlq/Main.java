import java.util.*;
import java.util.function.Consumer;

public class Main {
    static class Job {
        final String id; int attempts = 0; final int maxAttempts;
        Job(String id, int max) { this.id = id; this.maxAttempts = max; }
    }
    public static void main(String[] args) {
        Deque<Job> q = new ArrayDeque<>(List.of(new Job("ok", 3), new Job("fail", 2)));
        List<Job> done = new ArrayList<>(), dlq = new ArrayList<>();
        Consumer<Job> handler = j -> {
            if (j.id.equals("fail") || (j.id.equals("ok") && j.attempts == 1)) throw new RuntimeException("transient");
        };
        while (!q.isEmpty()) {
            Job j = q.pollFirst();
            j.attempts++;
            try { handler.accept(j); done.add(j); }
            catch (Exception e) { if (j.attempts < j.maxAttempts) q.addLast(j); else dlq.add(j); }
        }
        if (done.size() != 1 || dlq.size() != 1) throw new AssertionError("Queue mismatch");
        System.out.println("PASS 08-job-queue-dlq");
    }
}
