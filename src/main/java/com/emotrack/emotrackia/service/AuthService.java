package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.AuthResponse;
import com.emotrack.emotrackia.dto.LoginRequest;
import com.emotrack.emotrackia.dto.RegisterRequest;
import com.emotrack.emotrackia.entity.Estudiante;
import com.emotrack.emotrackia.entity.Profesional;
import com.emotrack.emotrackia.entity.Rol;
import com.emotrack.emotrackia.entity.Usuario;
import com.emotrack.emotrackia.enums.RolNombre;
import com.emotrack.emotrackia.exception.BusinessException;
import com.emotrack.emotrackia.repository.EstudianteRepository;
import com.emotrack.emotrackia.repository.ProfesionalRepository;
import com.emotrack.emotrackia.repository.RolRepository;
import com.emotrack.emotrackia.repository.UsuarioRepository;
import com.emotrack.emotrackia.security.JwtService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EstudianteRepository estudianteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }

        Rol rol = rolRepository.findByNombre(request.getRol())
                .orElseThrow(() -> new BusinessException("Rol no configurado: " + request.getRol()));

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(rol))
                .build();

        if (request.getRol() == RolNombre.ROLE_ESTUDIANTE) {
            Estudiante estudiante = Estudiante.builder()
                    .nombre(request.getNombre())
                    .programa(request.getPrograma())
                    .usuario(usuario)
                    .build();
            estudianteRepository.save(estudiante);
        } else if (request.getRol() == RolNombre.ROLE_PROFESIONAL) {
            Profesional profesional = Profesional.builder()
                    .nombre(request.getNombre())
                    .especialidad(request.getEspecialidad())
                    .usuario(usuario)
                    .build();
            profesionalRepository.save(profesional);
        } else {
            usuarioRepository.save(usuario);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        return buildAuthResponse(userDetails, usuarioRepository.findByEmail(request.getEmail()).orElseThrow());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Credenciales invalidas"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        return buildAuthResponse(userDetails, usuario);
    }

    private AuthResponse buildAuthResponse(UserDetails userDetails, Usuario usuario) {
        return AuthResponse.builder()
                .token(jwtService.generateToken(userDetails))
                .tipo("Bearer")
                .usuarioId(usuario.getId())
                .email(usuario.getEmail())
                .roles(usuario.getRoles().stream().map(rol -> rol.getNombre().name()).collect(Collectors.toSet()))
                .build();
    }
}
