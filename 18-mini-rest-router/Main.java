import java.util.*;
import java.util.function.Function;

public class Main {
    record Req(String method, String path) {}
    record Res(int status, String body) {}
    public static void main(String[] args) {
        Map<String, Function<Req, Res>> routes = Map.of("GET /health", r -> new Res(200, "ok"));
        Function<Req, Res> dispatch = r -> routes.getOrDefault(r.method() + " " + r.path(), x -> new Res(404, "not found")).apply(r);
        if (dispatch.apply(new Req("GET", "/health")).status() != 200 || dispatch.apply(new Req("GET", "/x")).status() != 404)
            throw new AssertionError("Router failed");
        System.out.println("PASS 18-mini-rest-router");
    }
}
