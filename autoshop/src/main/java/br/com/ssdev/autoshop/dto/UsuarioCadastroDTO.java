package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.UsuarioRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDTO(
        Long usuarioId,
        @NotBlank(message = "O nome de usuário é obrigatório !")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório !")
        @Email(message = "E-mail inválido !")
        String email,
        @NotBlank(message = "A senha é obrigatória !")
        @Size(min = 8, max = 16, message = "A senha deve conter entre 8 e 16 caracteres !")
        String senha,
        UsuarioRole funcao
) {
}
