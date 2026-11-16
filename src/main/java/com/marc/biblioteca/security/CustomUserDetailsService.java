package com.marc.biblioteca.security;

import com.marc.biblioteca.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarios;

    public CustomUserDetailsService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var usuario = usuarios.findByEmail(email.strip())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        boolean tienePassword = usuario.getPassword() != null && !usuario.getPassword().isBlank();

        return User.withUsername(usuario.getEmail())
                .password(tienePassword ? usuario.getPassword() : "")
                .roles(usuario.getRol())
                .disabled(!usuario.isActivo() || !tienePassword)
                .build();
    }
}
