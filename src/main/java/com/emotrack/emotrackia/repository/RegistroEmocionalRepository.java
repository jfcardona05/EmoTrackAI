package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.RegistroEmocional;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroEmocionalRepository extends JpaRepository<RegistroEmocional, Long> {

    @Override
    @EntityGraph(attributePaths = "estudiante")
    List<RegistroEmocional> findAll();
}
