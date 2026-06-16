package br.com.ssdev.autoshop.dto;

import br.com.ssdev.autoshop.models.CarroModel;
import br.com.ssdev.autoshop.models.ClienteModel;
import br.com.ssdev.autoshop.models.EnderecoModel;

import java.time.LocalDate;
import java.util.List;

public record ClienteExibicaoDTO(
        Long clienteId,
        String nome,
        String cpf,
        String email,
        List<CarroModel> carro,
        List<EnderecoModel> endereco,
        LocalDate dtNascimento,
        String telefone
) {
    public ClienteExibicaoDTO(ClienteModel clienteModel){
        this(
                clienteModel.getClienteId(),
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getCarros(),
                clienteModel.getEnderecos(),
                clienteModel.getDtNascimento(),
                clienteModel.getTelefone()
        );
    }
}
