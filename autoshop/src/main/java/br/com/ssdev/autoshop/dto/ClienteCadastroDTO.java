package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.model.CarroModel;
import br.com.ssdev.autoshop.model.EnderecoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClienteCadastroDTO(
        Long clinteId,
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String email,
        @NotNull
        CarroModel carro,
        @NotNull
        EnderecoModel endereco,
        @NotNull
        LocalDate dtNascimento,
        @NotBlank
        String telefone
) {
}
