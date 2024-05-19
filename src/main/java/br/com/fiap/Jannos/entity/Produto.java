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
@Table(name = "T_GOANDGETIT_PRODUTO")
public class Produto {

    @Id
    @SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "NM_PRODUTO")
    private String nome;

    @Column(name = "DS_PRODUTO")
    private String descricao;

    @Column(name = "TIPO_PRODUTO")
    private String tipo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ANUNCIO",
            referencedColumnName = "ID_ANUNCIO",
            foreignKey = @ForeignKey(
                    name = "FK_PRODUTO_ANUNCIO"
            )
    )
    private Anuncio anuncio;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "LOJA",
            referencedColumnName = "ID_LOJA",
            foreignKey = @ForeignKey(
                    name = "FK_PRODUTO_LOJA"
            )
    )
    private Loja loja;

}
