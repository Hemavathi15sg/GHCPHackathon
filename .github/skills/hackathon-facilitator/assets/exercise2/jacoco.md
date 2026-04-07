# JaCoCo Coverage Report
**Project:** user-management-service
**Branch:** feature/USER-142-registration
**Report date:** 2026-04-05
**CI gate:** ❌ FAILED (threshold: 80%)

---

## Overall Coverage

| Metric | Coverage |
|--------|----------|
| Line Coverage | 61% |
| Branch Coverage | 48% |
| Method Coverage | 72% |

---

## Coverage by Class (new/modified files only)

| Class | Line Coverage | Branch Coverage | Status |
|-------|--------------|-----------------|--------|
| `UserController` | 85% | 75% | ✅ |
| `UserService` | 45% | 32% | ❌ Below threshold |
| `UserRepository` | 100% | N/A (interface) | ✅ |

---

## Uncovered Lines in UserService

The following scenarios are not covered by tests:

- Password length validation branch (`password.length() < 8` → false path not tested)
- `DuplicateEmailException` throw path (no test for duplicate email)
- Successful registration happy path is tested but `userRepository.save()` call is not mocked correctly in 2 test cases — calls real DB

---

## Recommendation

`UserService` requires additional test cases:

1. Test: duplicate email → expect `DuplicateEmailException`
2. Test: password shorter than 8 chars → expect `IllegalArgumentException`
3. Test: valid input → expect `RegisterResponse` with non-null ID
4. Fix mock setup for `userRepository` in existing tests
