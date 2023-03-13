package org.mrl.utils;

import org.mrl.exception.PastryRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityContext {

    public static Long getCurrentUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (null != authentication && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return Optional.ofNullable(username).map(Long::parseLong)
                .orElseThrow(() -> new PastryRuntimeException(HttpStatus.FORBIDDEN.value()));
    }
}
