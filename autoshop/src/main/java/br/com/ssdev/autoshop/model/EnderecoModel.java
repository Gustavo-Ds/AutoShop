package br.com.ssdev.autoshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="TBL_ENDERECO")
@Entity
public class EnderecoModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ENDERECO_ID"
    )
    @SequenceGenerator(
            name = "SEQ_ENDERECO_ID",
            sequenceName = "SEQ_ENDERECO_ID",
            allocationSize = 1
    )
    @Column(name = "ENDERECO_ID")
    private Long enderecoId;
    private String rua;
    private String cidade;
    private String bairro;
    private String cep;
    private String numero;
}
