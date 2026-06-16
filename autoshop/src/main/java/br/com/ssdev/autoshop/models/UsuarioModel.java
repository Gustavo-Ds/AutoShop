package br.com.ssdev.autoshop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
@Entity
public class UsuarioModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_usuarios_id"
    )
    @SequenceGenerator(
            name = "seq_usuarios_id",
            sequenceName = "seq_usuarios_id",
            allocationSize = 1
    )
    @Column(name = "usuario_id")
    private Long usuarioId;
    private String nome;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UsuarioRole funcao;
}


