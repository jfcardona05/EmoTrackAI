package com.emotrack.emotrackia.controller;

import com.emotrack.emotrackia.dto.SeguimientoRequest;
import com.emotrack.emotrackia.dto.SeguimientoResponse;
import com.emotrack.emotrackia.service.SeguimientoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seguimientos")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    @PostMapping
    public ResponseEntity<SeguimientoResponse> create(@Valid @RequestBody SeguimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SeguimientoResponse>> findAll() {
        return ResponseEntity.ok(seguimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(seguimientoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoResponse> update(@PathVariable Long id, @Valid @RequestBody SeguimientoRequest request) {
        return ResponseEntity.ok(seguimientoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seguimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
