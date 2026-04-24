package com.emotrack.emotrackia.dto;

import com.emotrack.emotrackia.enums.RolNombre;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String programa;

    private String especialidad;

    @NotNull(message = "El rol es obligatorio")
    private RolNombre rol;
}
