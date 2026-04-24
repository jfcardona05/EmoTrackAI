package com.emotrack.emotrackia.dto;

import com.emotrack.emotrackia.enums.NivelAlerta;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AlertaResponse {
    private Long id;
    private NivelAlerta nivel;
    private String mensaje;
    private String estado;
    private LocalDateTime fecha;
    private Long registroId;
    private Long estudianteId;
}
