package io.demo.veterinary.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("securityService")
public class SecurityService {

    public boolean hasUser(Long idUser) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            return false;

        Object details = ((UsernamePasswordAuthenticationToken) authentication).getDetails();
        if (details == null)
            return false;

        Long tokenUserId = (Long) details;
        return tokenUserId.equals(idUser);
    }

    public boolean isAdmin() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean hasUserOrAdmin(Long idUser) {
        return hasUser(idUser) || isAdmin();
    }
}
