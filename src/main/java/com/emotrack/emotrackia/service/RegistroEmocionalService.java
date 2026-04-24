package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.AiAnalysisResult;
import com.emotrack.emotrackia.dto.RegistroRequest;
import com.emotrack.emotrackia.dto.RegistroResponse;
import com.emotrack.emotrackia.entity.Alerta;
import com.emotrack.emotrackia.entity.Estudiante;
import com.emotrack.emotrackia.entity.RegistroEmocional;
import com.emotrack.emotrackia.enums.NivelAlerta;
import com.emotrack.emotrackia.exception.ResourceNotFoundException;
import com.emotrack.emotrackia.repository.AlertaRepository;
import com.emotrack.emotrackia.repository.EstudianteRepository;
import com.emotrack.emotrackia.repository.RegistroEmocionalRepository;
import com.emotrack.emotrackia.util.MapperUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistroEmocionalService {

    private final RegistroEmocionalRepository registroRepository;
    private final EstudianteRepository estudianteRepository;
    private final AlertaRepository alertaRepository;
    private final IAService iaService;

    public List<RegistroResponse> findAll() {
        return registroRepository.findAll().stream()
                .map(registro -> MapperUtil.toRegistroResponse(registro, List.of(), null, null))
                .toList();
    }

    public RegistroResponse findById(Long id) {
        RegistroEmocional registro = getRegistro(id);
        Alerta alerta = alertaRepository.findAll().stream()
                .filter(item -> item.getRegistro().getId().equals(id))
                .findFirst()
                .orElse(null);
        return MapperUtil.toRegistroResponse(
                registro,
                List.of(),
                buildRecommendation(registro.getSentimiento().name()),
                alerta != null ? alerta.getMensaje() : null
        );
    }

    @Transactional
    public RegistroResponse create(RegistroRequest request) {
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + request.getEstudianteId()));

        AiAnalysisResult analysis = iaService.analizarTexto(request.getTexto());
        RegistroEmocional registro = RegistroEmocional.builder()
                .fecha(LocalDateTime.now())
                .texto(request.getTexto())
                .sentimiento(analysis.getSentimiento())
                .scoreRiesgo(analysis.getScoreRiesgo())
                .estudiante(estudiante)
                .build();
        RegistroEmocional saved = registroRepository.save(registro);

        String alertaGenerada = null;
        if (analysis.getNivelAlerta() != null) {
            NivelAlerta nivel = analysis.getNivelAlerta();
            Alerta alerta = Alerta.builder()
                    .nivel(nivel)
                    .mensaje(analysis.getMensajeAlerta())
                    .estado("ACTIVA")
                    .fecha(LocalDateTime.now())
                    .registro(saved)
                    .build();
            alertaRepository.save(alerta);
            alertaGenerada = analysis.getMensajeAlerta();
        }

        return MapperUtil.toRegistroResponse(saved, analysis.getPalabrasRiesgo(), analysis.getRecomendacion(), alertaGenerada);
    }

    @Transactional
    public RegistroResponse update(Long id, RegistroRequest request) {
        RegistroEmocional registro = getRegistro(id);
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + request.getEstudianteId()));

        AiAnalysisResult analysis = iaService.analizarTexto(request.getTexto());
        registro.setTexto(request.getTexto());
        registro.setEstudiante(estudiante);
        registro.setSentimiento(analysis.getSentimiento());
        registro.setScoreRiesgo(analysis.getScoreRiesgo());
        RegistroEmocional updated = registroRepository.save(registro);

        return MapperUtil.toRegistroResponse(updated, analysis.getPalabrasRiesgo(), analysis.getRecomendacion(), analysis.getMensajeAlerta());
    }

    @Transactional
    public void delete(Long id) {
        RegistroEmocional registro = getRegistro(id);
        registroRepository.delete(registro);
    }

    private RegistroEmocional getRegistro(Long id) {
        return registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro emocional no encontrado con id: " + id));
    }

    private String buildRecommendation(String sentimiento) {
        return switch (sentimiento) {
            case "POSITIVO" -> "Mantener habitos saludables y continuar con el registro emocional.";
            case "NEGATIVO" -> "Se recomienda apoyo inicial institucional.";
            default -> "Monitoreo preventivo y acceso a bienestar estudiantil.";
        };
    }
}
