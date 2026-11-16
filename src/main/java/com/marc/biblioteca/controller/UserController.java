package com.marc.biblioteca.controller;

import com.marc.biblioteca.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final LibroRepository libros;
    private final PrestamoRepository prestamos;
    public UserController(LibroRepository libros, PrestamoRepository prestamos) {
        this.libros = libros; this.prestamos = prestamos;
    }
    @GetMapping("/libros")
    public String libros(Model model) {
        model.addAttribute("libros", libros.findAll());
        return "libros/lista";
    }
    @GetMapping("/prestamos")
    public String prestamos(Model model, Authentication authentication) {
        model.addAttribute("prestamos", prestamos.findByUsuarioEmail(authentication.getName()));
        return "prestamos/lista";
    }
}
