package br.com.ssdev.autoshop.services;

import br.com.ssdev.autoshop.dto.UsuarioCadastroDTO;
import br.com.ssdev.autoshop.dto.UsuarioExibicaoDTO;
import br.com.ssdev.autoshop.models.UsuarioModel;
import br.com.ssdev.autoshop.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDTO cadastrarUsuario(UsuarioCadastroDTO usuarioCadastroDTO){
        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(usuarioCadastroDTO,usuario);
        usuarioRepository.save(usuario);
        return new UsuarioExibicaoDTO(usuario);
    }

    public void deletarUsuario(Long id){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()){
            usuarioRepository.delete(usuarioOptional.get());
        }
    }

}
