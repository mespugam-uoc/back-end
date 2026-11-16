package com.marc.biblioteca.controller;

import com.marc.biblioteca.form.RegistroForm;
import com.marc.biblioteca.model.Usuario;
import com.marc.biblioteca.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class AuthController {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarios, PasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("registro", new RegistroForm());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("registro") RegistroForm form, BindingResult errors) {
        if (!errors.hasFieldErrors("email") && usuarios.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", "duplicado", "Este email ya está registrado");
        }
        if (errors.hasErrors()) {
            return "registro";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre());
        usuario.setEmail(form.getEmail());
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        usuario.setRol("USER");
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDate.now());

        try {
            usuarios.save(usuario);
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("email", "duplicado", "Este email ya está registrado");
            return "registro";
        }
        return "redirect:/login?registrado";
    }

    @GetMapping("/acceso-denegado")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accesoDenegado() {
        return "acceso-denegado";
    }
}
