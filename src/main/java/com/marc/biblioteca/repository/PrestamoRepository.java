package com.marc.biblioteca.repository;

import com.marc.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    java.util.List<Prestamo> findByUsuarioEmail(String email);
    boolean existsByLibroId(Long libroId);
}