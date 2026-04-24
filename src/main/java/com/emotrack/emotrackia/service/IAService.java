package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.AiAnalysisResult;
import com.emotrack.emotrackia.enums.EstadoEmocional;
import com.emotrack.emotrackia.enums.NivelAlerta;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private static final List<String> POSITIVAS = List.of("feliz", "bien", "motivado");
    private static final List<String> NEUTRAS = List.of("normal", "estable");
    private static final List<String> NEGATIVAS = List.of("triste", "ansiedad", "agotado", "solo", "no puedo mas", "miedo");
    private static final List<String> ALTO_RIESGO = List.of("suicidio", "no quiero vivir", "no puedo mas");

    public AiAnalysisResult analizarTexto(String texto) {
        String normalized = normalize(texto);
        List<String> palabrasRiesgo = new ArrayList<>();
        int positivos = countMatches(normalized, POSITIVAS, null);
        int neutros = countMatches(normalized, NEUTRAS, null);
        int negativos = countMatches(normalized, NEGATIVAS, palabrasRiesgo);
        int altoRiesgo = countMatches(normalized, ALTO_RIESGO, palabrasRiesgo);

        EstadoEmocional sentimiento = EstadoEmocional.NEUTRO;
        if (negativos > Math.max(positivos, neutros)) {
            sentimiento = EstadoEmocional.NEGATIVO;
        } else if (positivos > Math.max(negativos, neutros)) {
            sentimiento = EstadoEmocional.POSITIVO;
        }

        int scoreRiesgo = 10 + (negativos * 20) + (altoRiesgo * 30);
        if (sentimiento == EstadoEmocional.POSITIVO) {
            scoreRiesgo = Math.max(5, scoreRiesgo - 15);
        }
        scoreRiesgo = Math.min(scoreRiesgo, 100);

        NivelAlerta nivelAlerta = null;
        String mensajeAlerta = null;
        if (altoRiesgo > 0 || scoreRiesgo >= 80) {
            nivelAlerta = NivelAlerta.ALTO;
            mensajeAlerta = "Se detectaron expresiones de alto riesgo emocional. Requiere revision institucional inmediata.";
        } else if (scoreRiesgo >= 50 || sentimiento == EstadoEmocional.NEGATIVO) {
            nivelAlerta = NivelAlerta.MEDIO;
            mensajeAlerta = "Se recomienda seguimiento preventivo por posible afectacion emocional.";
        } else if (scoreRiesgo >= 30) {
            nivelAlerta = NivelAlerta.BAJO;
            mensajeAlerta = "Se detectaron senales tempranas. Mantener observacion y recursos de apoyo.";
        }

        String recomendacion = switch (sentimiento) {
            case POSITIVO -> "Mantener habitos saludables y continuar con el registro emocional.";
            case NEUTRO -> "Se recomienda monitoreo preventivo y acceso a recursos de bienestar institucional.";
            case NEGATIVO -> "Se recomienda contacto con orientacion o bienestar universitario para apoyo inicial.";
        };

        return AiAnalysisResult.builder()
                .sentimiento(sentimiento)
                .scoreRiesgo(scoreRiesgo)
                .palabrasRiesgo(palabrasRiesgo.stream().distinct().toList())
                .nivelAlerta(nivelAlerta)
                .mensajeAlerta(mensajeAlerta)
                .recomendacion(recomendacion)
                .build();
    }

    private int countMatches(String texto, List<String> palabras, List<String> collector) {
        int count = 0;
        for (String palabra : palabras) {
            if (texto.contains(palabra)) {
                count++;
                if (collector != null) {
                    collector.add(palabra);
                }
            }
        }
        return count;
    }

    private String normalize(String texto) {
        return texto.toLowerCase(Locale.ROOT)
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");
    }
}
