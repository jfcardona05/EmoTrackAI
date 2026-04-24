package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.AlertaResponse;
import com.emotrack.emotrackia.entity.Alerta;
import com.emotrack.emotrackia.exception.ResourceNotFoundException;
import com.emotrack.emotrackia.repository.AlertaRepository;
import com.emotrack.emotrackia.util.MapperUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public List<AlertaResponse> findAll() {
        return alertaRepository.findAll().stream()
                .map(MapperUtil::toAlertaResponse)
                .toList();
    }

    public AlertaResponse findById(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + id));
        return MapperUtil.toAlertaResponse(alerta);
    }
}
