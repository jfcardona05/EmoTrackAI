package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.Alerta;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Override
    @EntityGraph(attributePaths = {"registro", "registro.estudiante"})
    List<Alerta> findAll();
}
