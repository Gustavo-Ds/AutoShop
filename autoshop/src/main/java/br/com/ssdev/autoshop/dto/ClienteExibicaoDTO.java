package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.model.CarroModel;
import br.com.ssdev.autoshop.model.ClienteModel;
import br.com.ssdev.autoshop.model.EnderecoModel;

import java.time.LocalDate;

public record ClienteExibicaoDTO(
        Long clienteId,
        String nome,
        String cpf,
        String email,
        CarroModel carro,
        EnderecoModel endereco,
        LocalDate dtNascimento,
        String telefone
) {
    public ClienteExibicaoDTO(ClienteModel clienteModel){
        this(
                clienteModel.getClienteId(),
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getCarro(),
                clienteModel.getEnderecoModel(),
                clienteModel.getDtNascimento(),
                clienteModel.getTelefone()
        );
    }
}
