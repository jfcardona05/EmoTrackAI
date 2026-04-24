package com.emotrack.emotrackia.controller;

import com.emotrack.emotrackia.dto.RegistroRequest;
import com.emotrack.emotrackia.dto.RegistroResponse;
import com.emotrack.emotrackia.service.RegistroEmocionalService;
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
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroEmocionalController {

    private final RegistroEmocionalService registroService;

    @PostMapping
    public ResponseEntity<RegistroResponse> create(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RegistroResponse>> findAll() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponse> update(@PathVariable Long id, @Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(registroService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
