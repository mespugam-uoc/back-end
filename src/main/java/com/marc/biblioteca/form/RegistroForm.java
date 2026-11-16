package com.marc.biblioteca.form;

import jakarta.validation.constraints.*;
import java.nio.charset.StandardCharsets;

public class RegistroForm {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre es demasiado largo")
    private String nombre;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Introduce un email válido")
    @Size(max = 255, message = "El email es demasiado largo")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 72, message = "Usa entre 8 y 72 caracteres")
    private String password;
    @NotBlank(message = "Confirma la contraseña")
    private String confirmacion;

    @AssertTrue(message = "Las contraseñas no coinciden")
    public boolean isPasswordCoincide() { return password != null && password.equals(confirmacion); }
    @AssertTrue(message = "La contraseña no puede superar 72 bytes UTF-8")
    public boolean isPasswordBytesValidos() {
        return password == null || password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre == null ? null : nombre.strip(); }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email == null ? null : email.strip(); }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmacion() { return confirmacion; }
    public void setConfirmacion(String confirmacion) { this.confirmacion = confirmacion; }
}
