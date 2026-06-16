package br.com.ssdev.autoshop.models;

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
@Table(name = "orcamentos")
@Entity
public class OrcamentoModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_orcamentos_id"
    )
    @SequenceGenerator(
            name = "seq_orcamentos_id",
            sequenceName = "seq_orcamentos_id",
            allocationSize = 1
    )
    @Column(name = "orcamento_id")
    private Long orcamentoId;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServicoModel ordemServico;
    private String itens;
    @Column(name = "mao_de_obra")
    private double maoDeObra;
    @Column(name = "is_aprovado")
    private boolean aprovado;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel tecnico;
}
