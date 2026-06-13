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
@Entity
@Table(name = "TBL_CARRO")
public class CarroModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_CARRO_ID"
    )
    @SequenceGenerator(
            name = "SEQ_CARRO_ID",
            sequenceName = "SEQ_CARRO_ID",
            allocationSize = 1
    )
    @Column(name = "CARRO_ID")
    private long carroId;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private String versao;
    private String vin;

}
