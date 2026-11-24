import java.util.*;

public class Main {
    static class Rbac {
        private final Map<String, Set<String>> roles = new HashMap<>();
        private final Map<String, Set<String>> userRoles = new HashMap<>();
        void defineRole(String name, Set<String> perms) { roles.put(name, perms); }
        void assign(String tenant, String user, String role) {
            userRoles.computeIfAbsent(tenant + ":" + user, k -> new HashSet<>()).add(role);
        }
        boolean can(String tenant, String user, String perm) {
            String res = perm.split(":")[0];
            for (String r : userRoles.getOrDefault(tenant + ":" + user, Set.of())) {
                Set<String> ps = roles.getOrDefault(r, Set.of());
                if (ps.contains("*") || ps.contains(perm) || ps.contains(res + ":*")) return true;
            }
            return false;
        }
    }
    public static void main(String[] args) {
        Rbac rbac = new Rbac();
        rbac.defineRole("editor", Set.of("docs:*", "billing:read"));
        rbac.assign("t1", "u1", "editor");
        if (!rbac.can("t1", "u1", "docs:delete") || rbac.can("t1", "u1", "billing:write") || rbac.can("t2", "u1", "docs:read"))
            throw new AssertionError("RBAC check failed");
        System.out.println("PASS 05-rbac-engine");
    }
}
