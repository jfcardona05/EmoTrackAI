package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.Seguimiento;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    @Override
    @EntityGraph(attributePaths = {"alerta", "profesional"})
    List<Seguimiento> findAll();
}
