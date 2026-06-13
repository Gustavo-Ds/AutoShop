package br.com.ssdev.autoshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ORDEM_SERVICO")
@Entity
public class OrdemServicoModel {
    @Id
    @SequenceGenerator(
            name = "SEQ_ORDEM_SERVICO_ID",
            sequenceName = "SEQ_ORDEM_SERVICO_ID",
            allocationSize = 1
    )
    @Column(name = "ORDEM_SERVICO_ID")
    private Long OrdemServicoId;
    private ClienteModel cliente;
    private OrcamentoModel orcamento;
    private CarroModel carro;
    @Column(name = "DT_ORDEM_SERVICO")
    private LocalDate dataOs;
    private double valor;
    private String observacoes;
    private OrdemServicoStatus status;


}
