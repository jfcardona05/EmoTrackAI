package com.emotrack.emotrackia.security;

import com.emotrack.emotrackia.entity.Usuario;
import com.emotrack.emotrackia.exception.ResourceNotFoundException;
import com.emotrack.emotrackia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + username));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .disabled(!usuario.getEnabled())
                .authorities(usuario.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getNombre().name()))
                        .toList())
                .build();
    }
}
