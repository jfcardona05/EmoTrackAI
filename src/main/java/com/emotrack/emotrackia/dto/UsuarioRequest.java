package com.emotrack.emotrackia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El estado enabled es obligatorio")
    private String enabled;
}
