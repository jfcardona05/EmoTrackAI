package com.emotrack.emotrackia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeguimientoRequest {

    @NotBlank(message = "La observacion es obligatoria")
    private String observacion;

    @NotNull(message = "La alerta es obligatoria")
    private Long alertaId;

    @NotNull(message = "El profesional es obligatorio")
    private Long profesionalId;
}
