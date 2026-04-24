package com.emotrack.emotrackia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {

    @NotBlank(message = "El texto del diario emocional es obligatorio")
    @Size(min = 5, max = 4000, message = "El texto debe tener entre 5 y 4000 caracteres")
    private String texto;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;
}
