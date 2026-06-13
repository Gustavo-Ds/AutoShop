package br.com.ssdev.autoshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ORCAMENTOS")
@Entity
public class OrcamentoModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ORCAMENTO_ID"
    )
    @SequenceGenerator(
            name = "SEQ_ORCAMENTO_ID",
            sequenceName = "SEQ_ORCAMENTO_ID",
            allocationSize = 1
    )
    private Long Orcamentoid;
    private List<String> itens;
    private double maoDeObra;
    private boolean aprovado;
    private LocalDate data;
    private UsuarioModel consultor;

}
