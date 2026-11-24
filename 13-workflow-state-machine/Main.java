import java.util.*;

public class Main {
    enum State { DRAFT, REVIEW, APPROVED, REJECTED, ARCHIVED }
    static final Map<State, Set<State>> RULES = Map.of(
        State.DRAFT, Set.of(State.REVIEW),
        State.REVIEW, Set.of(State.APPROVED, State.REJECTED),
        State.REJECTED, Set.of(State.DRAFT),
        State.APPROVED, Set.of(State.ARCHIVED),
        State.ARCHIVED, Set.of()
    );
    static State move(State cur, State next) {
        if (!RULES.get(cur).contains(next)) throw new IllegalStateException(cur + " -> " + next);
        return next;
    }
    public static void main(String[] args) {
        State s = move(move(State.DRAFT, State.REVIEW), State.APPROVED);
        boolean rejected = false;
        try { move(s, State.DRAFT); } catch (IllegalStateException e) { rejected = true; }
        if (!rejected) throw new AssertionError("Invalid transition allowed");
        System.out.println("PASS 13-workflow-state-machine");
    }
}
