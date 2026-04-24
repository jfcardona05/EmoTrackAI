package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.SeguimientoRequest;
import com.emotrack.emotrackia.dto.SeguimientoResponse;
import com.emotrack.emotrackia.entity.Alerta;
import com.emotrack.emotrackia.entity.Profesional;
import com.emotrack.emotrackia.entity.Seguimiento;
import com.emotrack.emotrackia.exception.ResourceNotFoundException;
import com.emotrack.emotrackia.repository.AlertaRepository;
import com.emotrack.emotrackia.repository.ProfesionalRepository;
import com.emotrack.emotrackia.repository.SeguimientoRepository;
import com.emotrack.emotrackia.util.MapperUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final AlertaRepository alertaRepository;
    private final ProfesionalRepository profesionalRepository;

    public List<SeguimientoResponse> findAll() {
        return seguimientoRepository.findAll().stream()
                .map(MapperUtil::toSeguimientoResponse)
                .toList();
    }

    public SeguimientoResponse findById(Long id) {
        return MapperUtil.toSeguimientoResponse(getSeguimiento(id));
    }

    @Transactional
    public SeguimientoResponse create(SeguimientoRequest request) {
        Alerta alerta = alertaRepository.findById(request.getAlertaId())
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + request.getAlertaId()));
        Profesional profesional = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con id: " + request.getProfesionalId()));

        Seguimiento seguimiento = Seguimiento.builder()
                .observacion(request.getObservacion())
                .fecha(LocalDateTime.now())
                .alerta(alerta)
                .profesional(profesional)
                .build();

        alerta.setEstado("EN_SEGUIMIENTO");
        Seguimiento saved = seguimientoRepository.save(seguimiento);
        return MapperUtil.toSeguimientoResponse(saved);
    }

    @Transactional
    public SeguimientoResponse update(Long id, SeguimientoRequest request) {
        Seguimiento seguimiento = getSeguimiento(id);
        Alerta alerta = alertaRepository.findById(request.getAlertaId())
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + request.getAlertaId()));
        Profesional profesional = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con id: " + request.getProfesionalId()));

        seguimiento.setObservacion(request.getObservacion());
        seguimiento.setAlerta(alerta);
        seguimiento.setProfesional(profesional);
        return MapperUtil.toSeguimientoResponse(seguimientoRepository.save(seguimiento));
    }

    @Transactional
    public void delete(Long id) {
        seguimientoRepository.delete(getSeguimiento(id));
    }

    private Seguimiento getSeguimiento(Long id) {
        return seguimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado con id: " + id));
    }
}
