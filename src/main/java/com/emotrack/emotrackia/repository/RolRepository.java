package com.emotrack.emotrackia.repository;

import com.emotrack.emotrackia.entity.Rol;
import com.emotrack.emotrackia.enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(RolNombre nombre);
}
