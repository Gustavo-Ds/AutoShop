package br.com.ssdev.autoshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "TBL_CLIENTE")
@Entity
public class ClienteModel {

    @Id
    @SequenceGenerator(
            name = "SEQ_CLIENTE_ID",
            sequenceName = "SEQ_CLIENTE_ID",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTES_ID")
    @Column(name = "CLIENTE_ID")
    private Long clienteId;
    private String nome;
    private String cpf;
    private String email;
    private CarroModel carro;
    private EnderecoModel enderecoModel;
    private LocalDate dtNascimento;
    private String telefone;
}
