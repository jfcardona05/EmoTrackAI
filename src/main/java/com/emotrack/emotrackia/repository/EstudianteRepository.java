package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.Estudiante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByUsuarioEmail(String email);
}
