package com.marc.biblioteca.config;

import com.marc.biblioteca.model.Usuario;
import com.marc.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarios, PasswordEncoder passwordEncoder,
                             @Value("${ADMIN_EMAIL:}") String email,
                             @Value("${ADMIN_PASSWORD:}") String password) {
        return args -> {
            if (email.isBlank() || password.isBlank() || usuarios.existsByRol("ADMIN")
                    || usuarios.existsByEmail(email.strip())) {
                return;
            }

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail(email.strip());
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRol("ADMIN");
            admin.setActivo(true);
            admin.setFechaRegistro(LocalDate.now());
            usuarios.save(admin);
        };
    }
}
