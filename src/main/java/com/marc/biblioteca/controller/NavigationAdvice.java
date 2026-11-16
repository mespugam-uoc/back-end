package com.marc.biblioteca.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class NavigationAdvice {
    @ModelAttribute("esAdmin")
    public boolean esAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
