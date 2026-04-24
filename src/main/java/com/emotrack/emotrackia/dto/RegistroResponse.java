package com.emotrack.emotrackia.dto;

import com.emotrack.emotrackia.enums.EstadoEmocional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegistroResponse {
    private Long id;
    private LocalDateTime fecha;
    private String texto;
    private EstadoEmocional sentimiento;
    private Integer scoreRiesgo;
    private Long estudianteId;
    private String estudianteNombre;
    private List<String> palabrasRiesgo;
    private String recomendacion;
    private String alertaGenerada;
}
