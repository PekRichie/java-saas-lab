import java.util.*;
import java.util.function.Consumer;

public class Main {
    static class EventBus {
        private final Map<String, List<Consumer<String>>> subs = new HashMap<>();
        void on(String topic, Consumer<String> fn) { subs.computeIfAbsent(topic, k -> new ArrayList<>()).add(fn); }
        void emit(String topic, String payload) { subs.getOrDefault(topic, List.of()).forEach(fn -> fn.accept(payload)); }
    }
    public static void main(String[] args) {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        bus.on("invoice.paid", p -> log.add("email:" + p));
        bus.on("invoice.paid", p -> log.add("audit:" + p));
        bus.emit("invoice.paid", "INV-101");
        if (log.size() != 2) throw new AssertionError("PubSub failed");
        System.out.println("PASS 16-event-bus-pubsub");
    }
}
