// sample/bad_example.java
// PURPOSE: Deliberately non-compliant Java file for Exercise 1, Step 5.
// Participants ask Copilot to review this against .github/copilot-instructions.md
// and output/coding-standards.md produced in earlier steps.
//
// VIOLATIONS PLANTED (do not fix before the session):
// V1 — NAMING: snake_case variable names instead of camelCase
// V2 — NAMING: generic name `data` for a method parameter
// V3 — LOGGING: plain System.out.println instead of structured logger
// V4 — LOGGING: password logged in plain text (PII/security violation)
// V5 — ERROR HANDLING: empty catch block (exception swallowed)
// V6 — SECURITY: hardcoded API key in source code
// V7 — SECURITY: raw SQL string concatenation (SQL injection risk)
// V8 — TESTING: no tests provided for this class
// V9 — PERFORMANCE: DB query inside a loop (N+1 pattern)

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class user_registration_handler {   // V1: class name snake_case, not PascalCase

    private static final String api_key = "sk-prod-abc123secret";   // V6: hardcoded secret

    public void register_user(Object data, String raw_password) {   // V1: snake_case method/params, V2: generic 'data'

        // V3 + V4: plain println, logs raw password
        System.out.println("Registering user: " + data + " password=" + raw_password);

        String user_email = data.toString();   // V1: snake_case local variable

        // V7: SQL injection — string concatenation
        String sql = "SELECT * FROM users WHERE email = '" + user_email + "'";

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Query done");
        } catch (Exception e) {
            // V5: empty catch block — exception swallowed silently
        }

        // V9: N+1 — DB call inside a loop
        List<String> role_list = getRoles();
        for (String r : role_list) {
            saveUserRole(user_email, r);   // V9: separate DB call per role
        }

        System.out.println("Done");   // V3: println again
    }

    private Connection getConnection() throws Exception {
        return null; // placeholder
    }

    private List<String> getRoles() {
        return List.of("USER", "VIEWER");
    }

    private void saveUserRole(String email, String role) {
        // placeholder — would issue DB call per iteration
    }
}
