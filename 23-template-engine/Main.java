import java.util.*;
import java.util.regex.*;

public class Main {
    static String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
    static String render(String tpl, Map<String, String> ctx) {
        Matcher m = Pattern.compile("\\{\\{\\s*([a-zA-Z0-9_]+)\\s*\\}\\}").matcher(tpl);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            if (!ctx.containsKey(key)) throw new IllegalArgumentException("Missing: " + key);
            m.appendReplacement(sb, Matcher.quoteReplacement(esc(ctx.get(key))));
        }
        m.appendTail(sb);
        return sb.toString();
    }
    public static void main(String[] args) {
        String out = render("<p>Szia {{ name }}!</p>", Map.of("name", "<b>Richárd</b>"));
        if (!out.contains("&lt;b&gt;Richárd&lt;/b&gt;")) throw new AssertionError("Escape failed: " + out);
        System.out.println("PASS 23-template-engine");
    }
}
