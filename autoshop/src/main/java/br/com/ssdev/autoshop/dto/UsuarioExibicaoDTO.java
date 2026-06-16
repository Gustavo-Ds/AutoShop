package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.UsuarioModel;

public record UsuarioExibicaoDTO(
        Long usuarioId,
        String nome,
        String email,
        String funcao
) {
   public UsuarioExibicaoDTO(UsuarioModel usuarioModel){
       this(
               usuarioModel.getUsuarioId(),
               usuarioModel.getNome(),
               usuarioModel.getEmail(),
               usuarioModel.getFuncao().getRole()
       );
   }
}
