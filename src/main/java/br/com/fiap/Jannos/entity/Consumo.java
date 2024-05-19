package br.com.fiap.Jannos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "T_GOANDGETIT_CONSUMO")
public class Consumo {

    @Id
    @SequenceGenerator(name = "SQ_CONSUMO", sequenceName = "SQ_CONSUMO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CONSUMO")
    @Column(name = "ID_CONSUMO")
    private Long id;

    @Column(name = "QT_CONSUMO")
    private String quantidade;

    @Column(name = "DS_CONSUMO")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "CLIENTE",
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @ForeignKey(
                    name = "FK_CONSUMO_CLIENTE"
            )
    )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ANUNCIO",
            referencedColumnName = "ID_ANUNCIO",
            foreignKey = @ForeignKey(
                    name = "FK_CONSUMO_ANUNCIO"
            )
    )
    private Anuncio anuncio;

}
