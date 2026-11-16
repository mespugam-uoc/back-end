package com.marc.biblioteca.controller;

import com.marc.biblioteca.repository.PrestamoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/prestamos")
public class PrestamoController {

    private final PrestamoRepository prestamoRepository;

    public PrestamoController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "prestamos/lista";
    }
}