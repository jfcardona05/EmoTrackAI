package com.emotrack.emotrackia.util;

import com.emotrack.emotrackia.dto.AlertaResponse;
import com.emotrack.emotrackia.dto.RegistroResponse;
import com.emotrack.emotrackia.dto.SeguimientoResponse;
import com.emotrack.emotrackia.dto.UsuarioResponse;
import com.emotrack.emotrackia.entity.Alerta;
import com.emotrack.emotrackia.entity.RegistroEmocional;
import com.emotrack.emotrackia.entity.Seguimiento;
import com.emotrack.emotrackia.entity.Usuario;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MapperUtil {

    private MapperUtil() {
    }

    public static UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .enabled(usuario.getEnabled())
                .roles(mapRoles(usuario))
                .build();
    }

    public static RegistroResponse toRegistroResponse(RegistroEmocional registro, List<String> palabrasRiesgo, String recomendacion, String alertaGenerada) {
        return RegistroResponse.builder()
                .id(registro.getId())
                .fecha(registro.getFecha())
                .texto(registro.getTexto())
                .sentimiento(registro.getSentimiento())
                .scoreRiesgo(registro.getScoreRiesgo())
                .estudianteId(registro.getEstudiante().getId())
                .estudianteNombre(registro.getEstudiante().getNombre())
                .palabrasRiesgo(palabrasRiesgo)
                .recomendacion(recomendacion)
                .alertaGenerada(alertaGenerada)
                .build();
    }

    public static AlertaResponse toAlertaResponse(Alerta alerta) {
        return AlertaResponse.builder()
                .id(alerta.getId())
                .nivel(alerta.getNivel())
                .mensaje(alerta.getMensaje())
                .estado(alerta.getEstado())
                .fecha(alerta.getFecha())
                .registroId(alerta.getRegistro().getId())
                .estudianteId(alerta.getRegistro().getEstudiante().getId())
                .build();
    }

    public static SeguimientoResponse toSeguimientoResponse(Seguimiento seguimiento) {
        return SeguimientoResponse.builder()
                .id(seguimiento.getId())
                .observacion(seguimiento.getObservacion())
                .fecha(seguimiento.getFecha())
                .alertaId(seguimiento.getAlerta().getId())
                .profesionalId(seguimiento.getProfesional().getId())
                .profesionalNombre(seguimiento.getProfesional().getNombre())
                .build();
    }

    private static Set<String> mapRoles(Usuario usuario) {
        return usuario.getRoles().stream()
                .map(rol -> rol.getNombre().name())
                .collect(Collectors.toSet());
    }
}
