package br.com.ssdev.autoshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordens_de_servico")
@Entity
public class OrdemServicoModel {
    @Id
    @SequenceGenerator(
            name = "seq_ordem_servico_id",
            sequenceName = "seq_ordem_servico_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ordem_servico_id"
    )
    @Column(name = "ordem_servico_id")
    private Long ordemServicoId;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;
    @OneToMany(mappedBy = "ordemServico")
    private Set<OrcamentoModel> orcamento;
    @ManyToOne
    @JoinColumn(name = "carro_id")
    private CarroModel carro;
    @Column(name = "data_ordem_servico")
    private LocalDate dataOs;
    private double valor;
    private String observacoes;
    @Enumerated(EnumType.STRING)
    private OrdemServicoStatus status;


}
