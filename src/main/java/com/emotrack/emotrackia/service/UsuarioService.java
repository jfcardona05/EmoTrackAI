package com.emotrack.emotrackia.service;

import com.emotrack.emotrackia.dto.UsuarioRequest;
import com.emotrack.emotrackia.dto.UsuarioResponse;
import com.emotrack.emotrackia.entity.Usuario;
import com.emotrack.emotrackia.exception.BusinessException;
import com.emotrack.emotrackia.exception.ResourceNotFoundException;
import com.emotrack.emotrackia.repository.UsuarioRepository;
import com.emotrack.emotrackia.util.MapperUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(MapperUtil::toUsuarioResponse)
                .toList();
    }

    public UsuarioResponse findById(Long id) {
        return MapperUtil.toUsuarioResponse(getEntity(id));
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario usuario = getEntity(id);
        if (!usuario.getEmail().equals(request.getEmail()) && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }
        usuario.setEmail(request.getEmail());
        usuario.setEnabled(Boolean.parseBoolean(request.getEnabled()));
        return MapperUtil.toUsuarioResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = getEntity(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario getEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }
}
