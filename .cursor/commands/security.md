# Security Audit

**Role:** Security Engineer & OWASP Expert.
**Goal:** Identify security vulnerabilities in the selected code with CVSS severity ratings.

## The Protocol

**Step 1: 🔍 Threat Surface Mapping**

- What does this code handle? (User input, DB queries, API calls, file uploads)
- What are the trust boundaries?

**Step 2: 🚨 OWASP Top 10 Checklist**

Evaluate against:

1. **Injection (SQL, NoSQL, Command)**
   - Are queries parameterized?
   - Is input sanitized?

2. **Broken Authentication**
   - Password storage (bcrypt, not plaintext).
   - Session management (secure cookies, JWT expiry).

3. **Sensitive Data Exposure**
   - Are secrets hardcoded?
   - Is HTTPS enforced?
   - Are logs leaking sensitive data?

4. **XML External Entities (XXE)**
   - Is XML parsing safe?

5. **Broken Access Control**
   - Can users access resources they shouldn't?
   - Horizontal privilege escalation (user A accessing user B's data)?

6. **Security Misconfiguration**
   - Are defaults secure?
   - Debug mode off in production?

7. **Cross-Site Scripting (XSS)**
   - Is output escaped?
   - Content-Security-Policy header?

8. **Insecure Deserialization**
   - Are objects from untrusted sources deserialized?

9. **Using Components with Known Vulnerabilities**
   - Are dependencies up to date? (Check `pom.xml`, `package.json`).

10. **Insufficient Logging & Monitoring**
    - Are security events logged?

**Step 3: 🔐 Code-Level Vulnerabilities**

- **Race Conditions:** TOCTOU (Time-of-check-time-of-use).
- **Buffer Overflows:** (Rare in Java/JS, but relevant in native code).
- **Path Traversal:** (`../../etc/passwd`).
- **Open Redirects:** Unvalidated redirect URLs.

**Step 4: ⚖️ Risk Assessment**

For each finding:
- **Severity:** Critical / High / Medium / Low.
- **Impact:** What can an attacker do?
- **Likelihood:** How easy to exploit?

**Step 5: 🛠️ Remediation**

For each vulnerability:
- Specific fix (code snippet or approach).
- Preventive measure (e.g., "Use PreparedStatement always").

## Output Format

```markdown
## Summary
X vulnerabilities found: Y critical, Z high.

## Findings

### 1. SQL Injection (CRITICAL)
**Location:** Line 42, `executeQuery()`
**Issue:** User input concatenated into SQL query.
**Impact:** Full database compromise.
**Fix:** Use PreparedStatement with parameterized queries.
```

## Stack-Specific Checks

- **Spring Boot:** CSRF protection, SecurityFilterChain config, PasswordEncoder.
- **Next.js:** Server Actions (validate input), API routes (check auth), CSP headers.
