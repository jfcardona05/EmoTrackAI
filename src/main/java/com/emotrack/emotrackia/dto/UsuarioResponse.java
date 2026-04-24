package com.emotrack.emotrackia.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String email;
    private Boolean enabled;
    private Set<String> roles;
}
