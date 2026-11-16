package com.marc.biblioteca.controller;

import com.marc.biblioteca.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final LibroRepository libros;
    private final UsuarioRepository usuarios;
    private final PrestamoRepository prestamos;
    public HomeController(LibroRepository libros, UsuarioRepository usuarios, PrestamoRepository prestamos) {
        this.libros = libros; this.usuarios = usuarios; this.prestamos = prestamos;
    }
    @GetMapping("/")
    public String home(Authentication auth) {
        return "redirect:" + (isAdmin(auth) ? "/admin/inicio" : "/user/inicio");
    }
    @GetMapping("/admin/inicio")
    public String admin(Model model) {
        model.addAttribute("totalLibros", libros.count());
        model.addAttribute("totalUsuarios", usuarios.count());
        model.addAttribute("totalPrestamos", prestamos.count());
        return "index";
    }
    @GetMapping("/user/inicio")
    public String user(Model model, Authentication auth) {
        model.addAttribute("totalLibros", libros.count());
        model.addAttribute("totalPrestamos", prestamos.findByUsuarioEmail(auth.getName()).size());
        return "user-inicio";
    }
    @GetMapping("/libros")
    public String libros(Authentication auth) { return "redirect:" + (isAdmin(auth) ? "/admin/libros" : "/user/libros"); }
    @GetMapping("/usuarios")
    public String usuarios() { return "redirect:/admin/usuarios"; }
    @GetMapping("/prestamos")
    public String prestamos(Authentication auth) { return "redirect:" + (isAdmin(auth) ? "/admin/prestamos" : "/user/prestamos"); }
    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
