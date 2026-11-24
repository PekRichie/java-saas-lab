import java.util.*;

public class Main {
    record Doc(String tenant, String title) {}
    static final ThreadLocal<String> TENANT = new ThreadLocal<>();
    public static void main(String[] args) {
        List<Doc> db = List.of(new Doc("t1", "A"), new Doc("t2", "B"), new Doc("t1", "C"));
        TENANT.set("t1");
        try {
            List<Doc> vis = db.stream().filter(d -> d.tenant().equals(TENANT.get())).toList();
            if (vis.size() != 2) throw new AssertionError("Isolation broken");
        } finally { TENANT.remove(); }
        System.out.println("PASS 15-tenant-context-isolation");
    }
}
