import java.util.*;

public class Main {
    record UserDto(String email, String password) {}
    static List<String> validate(UserDto u) {
        List<String> errs = new ArrayList<>();
        if (u.email() == null || !u.email().contains("@")) errs.add("Érvénytelen e-mail");
        if (u.password() == null || u.password().length() < 8) errs.add("Túl rövid jelszó");
        return errs;
    }
    public static void main(String[] args) {
        if (validate(new UserDto("bad", "123")).size() != 2 || !validate(new UserDto("a@b.hu", "StrongPass1")).isEmpty())
            throw new AssertionError("Validation failed");
        System.out.println("PASS 17-validation-pipeline");
    }
}
