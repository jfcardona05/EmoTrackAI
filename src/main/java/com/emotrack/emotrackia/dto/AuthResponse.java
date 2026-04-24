package com.emotrack.emotrackia.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tipo;
    private Long usuarioId;
    private String email;
    private Set<String> roles;
}
