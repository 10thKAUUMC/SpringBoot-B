package com.example.umc10th.global.util;

public final class AuthMemberResolver {

    private AuthMemberResolver() {
    }

    public static Long resolveMemberId(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            return 1L;
        }

        String token = authorization.replace("Bearer", "").trim();
        try {
            return Long.parseLong(token);
        } catch (NumberFormatException e) {
            return 1L;
        }
    }
}
