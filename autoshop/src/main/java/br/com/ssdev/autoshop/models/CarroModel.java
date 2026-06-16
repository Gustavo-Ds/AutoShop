package br.com.ssdev.autoshop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carros")
public class CarroModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_carro_id"
    )
    @SequenceGenerator(
            name = "seq_carro_id",
            sequenceName = "seq_carro_id",
            allocationSize = 1
    )
    @Column(name = "carro_id")
    private long carroId;
    @OneToMany(mappedBy = "carro")
    private List<OrdemServicoModel> ordens;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel dono;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private String versao;
    private String chassi;
    private String cambio;
}
