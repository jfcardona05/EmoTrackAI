package com.emotrack.emotrackia.config;

import com.emotrack.emotrackia.entity.Profesional;
import com.emotrack.emotrackia.entity.Rol;
import com.emotrack.emotrackia.entity.Usuario;
import com.emotrack.emotrackia.enums.RolNombre;
import com.emotrack.emotrackia.repository.ProfesionalRepository;
import com.emotrack.emotrackia.repository.RolRepository;
import com.emotrack.emotrackia.repository.UsuarioRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            createRoleIfMissing(RolNombre.ROLE_ADMIN);
            createRoleIfMissing(RolNombre.ROLE_ESTUDIANTE);
            createRoleIfMissing(RolNombre.ROLE_PROFESIONAL);

            if (!usuarioRepository.existsByEmail("admin@emotrack.ai")) {
                Rol adminRole = rolRepository.findByNombre(RolNombre.ROLE_ADMIN).orElseThrow();
                Usuario admin = Usuario.builder()
                        .email("admin@emotrack.ai")
                        .password(passwordEncoder.encode("Admin123*"))
                        .enabled(true)
                        .roles(Set.of(adminRole))
                        .build();
                usuarioRepository.save(admin);
            }

            if (!usuarioRepository.existsByEmail("profesional@emotrack.ai")) {
                Rol profesionalRole = rolRepository.findByNombre(RolNombre.ROLE_PROFESIONAL).orElseThrow();
                Usuario usuarioProfesional = Usuario.builder()
                        .email("profesional@emotrack.ai")
                        .password(passwordEncoder.encode("Profesional123*"))
                        .enabled(true)
                        .roles(Set.of(profesionalRole))
                        .build();

                Profesional profesional = Profesional.builder()
                        .nombre("Profesional Demo")
                        .especialidad("Psicologia Clinica")
                        .usuario(usuarioProfesional)
                        .build();
                profesionalRepository.save(profesional);
            }
        };
    }

    private void createRoleIfMissing(RolNombre rolNombre) {
        rolRepository.findByNombre(rolNombre)
                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(rolNombre).build()));
    }
}
