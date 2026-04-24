package com.emotrack.emotrackia.controller;

import com.emotrack.emotrackia.dto.AlertaResponse;
import com.emotrack.emotrackia.service.AlertaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaResponse>> findAll() {
        return ResponseEntity.ok(alertaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.findById(id));
    }
}
