package br.com.ssdev.autoshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "TBL_PRODUTIVOS")
@Entity
public class ProdutivoModel {
    @Id
    @SequenceGenerator(
            name = "SEQ_PRODUTIVO_ID",
            sequenceName = "SEQ_PRODUTIVO_ID",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_PRODUTIVO_ID"
    )
    @Column(name = "PRODUTIVO_ID")
    private Long produtivoId;
    private String nome;
    private boolean ativo;
}
