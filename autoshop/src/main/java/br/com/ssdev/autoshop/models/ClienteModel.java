package br.com.ssdev.autoshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "clientes")
@Entity
public class ClienteModel {

    @Id
    @SequenceGenerator(
            name = "seq_clientes_id",
            sequenceName = "seq_clientes_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_clientes_id")
    @Column(name = "cliente_id")
    private Long clienteId;
    private String nome;
    private String cpf;
    private String email;
    @OneToMany(mappedBy = "dono")
    private List<CarroModel> carros;
    @OneToMany(mappedBy = "cliente")
    private List<EnderecoModel> enderecos;
    @OneToMany(mappedBy = "cliente")
    private List<OrdemServicoModel> ordemServico;
    @Column(name = "data_nascimento")
    private LocalDate dtNascimento;
    private String telefone;
}
