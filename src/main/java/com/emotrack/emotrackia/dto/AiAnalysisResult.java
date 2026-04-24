package com.emotrack.emotrackia.dto;

import com.emotrack.emotrackia.enums.EstadoEmocional;
import com.emotrack.emotrackia.enums.NivelAlerta;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AiAnalysisResult {
    private EstadoEmocional sentimiento;
    private int scoreRiesgo;
    private List<String> palabrasRiesgo;
    private NivelAlerta nivelAlerta;
    private String mensajeAlerta;
    private String recomendacion;
}
