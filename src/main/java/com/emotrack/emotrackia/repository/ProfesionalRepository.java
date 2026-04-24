package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.Profesional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
    Optional<Profesional> findByUsuarioEmail(String email);
}
