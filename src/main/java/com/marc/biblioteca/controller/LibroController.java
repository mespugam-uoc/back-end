package com.marc.biblioteca.controller;

import com.marc.biblioteca.model.Libro;
import com.marc.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Libro libro) {
        libroRepository.save(libro);
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        model.addAttribute("libro", libro);
        return "libros/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        libroRepository.deleteById(id);
        return "redirect:/libros";
    }
}