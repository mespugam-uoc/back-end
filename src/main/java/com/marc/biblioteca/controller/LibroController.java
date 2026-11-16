package com.marc.biblioteca.controller;

import com.marc.biblioteca.model.Libro;
import com.marc.biblioteca.repository.LibroRepository;
import com.marc.biblioteca.repository.PrestamoRepository;
import com.marc.biblioteca.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/libros")
public class LibroController {

    private final LibroRepository libros;
    private final UsuarioRepository usuarios;
    private final PrestamoRepository prestamos;

    public LibroController(LibroRepository libros, UsuarioRepository usuarios, PrestamoRepository prestamos) {
        this.libros = libros;
        this.usuarios = usuarios;
        this.prestamos = prestamos;
    }

    @InitBinder("libro")
    public void camposEditables(WebDataBinder binder) {
        binder.setAllowedFields("id", "titulo", "autor", "isbn", "disponible");
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libros.findAll());
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("libro") Libro formulario, BindingResult errors,
                          Authentication authentication) {
        if (errors.hasErrors()) {
            return "libros/formulario";
        }

        Libro libro;
        if (formulario.getId() == null) {
            libro = new Libro();
            libro.setCreadoPor(usuarios.findByEmail(authentication.getName()).orElseThrow());
        } else {
            libro = buscar(formulario.getId());
        }
        libro.setTitulo(formulario.getTitulo());
        libro.setAutor(formulario.getAutor());
        libro.setIsbn(formulario.getIsbn() == null || formulario.getIsbn().isBlank()
                ? null : formulario.getIsbn().strip());
        libro.setDisponible(formulario.isDisponible());

        try {
            libros.save(libro);
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("isbn", "duplicado", "Este ISBN ya está registrado");
            return "libros/formulario";
        }
        return "redirect:/admin/libros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("libro", buscar(id));
        return "libros/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        Libro libro = buscar(id);
        if (prestamos.existsByLibroId(id)) {
            redirect.addFlashAttribute("error", "No se puede eliminar un libro con préstamos.");
        } else {
            try {
                libros.delete(libro);
            } catch (DataIntegrityViolationException ex) {
                redirect.addFlashAttribute("error", "No se puede eliminar un libro con préstamos.");
            }
        }
        return "redirect:/admin/libros";
    }

    private Libro buscar(Long id) {
        return libros.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }
}
