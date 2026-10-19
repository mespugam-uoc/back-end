package com.marc.biblioteca.config;

import com.marc.biblioteca.model.Libro;
import com.marc.biblioteca.model.Prestamo;
import com.marc.biblioteca.model.Usuario;
import com.marc.biblioteca.repository.LibroRepository;
import com.marc.biblioteca.repository.PrestamoRepository;
import com.marc.biblioteca.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            PrestamoRepository prestamoRepository) {

        return args -> {

            if (usuarioRepository.count() > 0) {
                return;
            }

            // Usuarios
            Usuario marc = new Usuario();
            marc.setNombre("Marc Espuga");
            marc.setEmail("marc@example.com");
            marc.setFechaRegistro(LocalDate.now());

            Usuario laura = new Usuario();
            laura.setNombre("Laura García");
            laura.setEmail("laura@example.com");
            laura.setFechaRegistro(LocalDate.now());

            usuarioRepository.save(marc);
            usuarioRepository.save(laura);

            // Libros
            Libro cleanCode = new Libro();
            cleanCode.setTitulo("Clean Code");
            cleanCode.setAutor("Robert C. Martin");
            cleanCode.setIsbn("9780132350884");
            cleanCode.setDisponible(false);

            Libro effectiveJava = new Libro();
            effectiveJava.setTitulo("Effective Java");
            effectiveJava.setAutor("Joshua Bloch");
            effectiveJava.setIsbn("9780134685991");
            effectiveJava.setDisponible(false);

            Libro spring = new Libro();
            spring.setTitulo("Spring in Action");
            spring.setAutor("Craig Walls");
            spring.setIsbn("9781617297571");
            spring.setDisponible(true);

            libroRepository.save(cleanCode);
            libroRepository.save(effectiveJava);
            libroRepository.save(spring);

            // Préstamos
            Prestamo prestamo1 = new Prestamo();
            prestamo1.setUsuario(marc);
            prestamo1.setLibro(cleanCode);
            prestamo1.setFechaPrestamo(LocalDate.now());
            prestamo1.setDevuelto(false);

            Prestamo prestamo2 = new Prestamo();
            prestamo2.setUsuario(laura);
            prestamo2.setLibro(effectiveJava);
            prestamo2.setFechaPrestamo(LocalDate.now());
            prestamo2.setDevuelto(false);

            prestamoRepository.save(prestamo1);
            prestamoRepository.save(prestamo2);
        };
    }
}