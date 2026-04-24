package com.emotrack.emotrackia.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeguimientoResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fecha;
    private Long alertaId;
    private Long profesionalId;
    private String profesionalNombre;
}
