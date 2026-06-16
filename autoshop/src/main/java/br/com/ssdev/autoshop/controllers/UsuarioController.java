package br.com.ssdev.autoshop.controllers;


import br.com.ssdev.autoshop.dto.UsuarioCadastroDTO;
import br.com.ssdev.autoshop.dto.UsuarioExibicaoDTO;
import br.com.ssdev.autoshop.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoDTO salvarUsuario(@Valid @RequestBody UsuarioCadastroDTO usuarioDTO){
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }

    @DeleteMapping("/apagar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apagarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
    }
}
