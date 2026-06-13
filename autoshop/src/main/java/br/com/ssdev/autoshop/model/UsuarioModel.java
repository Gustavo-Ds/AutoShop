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
@Table(name = "TBL_USUARIOS")
@Entity
public class UsuarioModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_USUARIO_ID"
    )
    @SequenceGenerator(
            name = "SEQ_USUARIO_ID",
            sequenceName = "SEQ_USAURIO_ID",
            allocationSize = 1
    )
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    private String nome;
    private String senha;
    private UsuarioRole papel;
}
