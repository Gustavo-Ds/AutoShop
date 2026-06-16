package br.com.ssdev.autoshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="enderecos")
@Entity
public class EnderecoModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_endereco_id"
    )
    @SequenceGenerator(
            name = "seq_endereco_id",
            sequenceName = "seq_endereco_id",
            allocationSize = 1
    )
    @Column(name = "endereco_id")
    private Long enderecoId;
    @ManyToOne
    @JoinColumn(name = "enderecos")
    private ClienteModel cliente;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String cep;
    private String numero;
}
