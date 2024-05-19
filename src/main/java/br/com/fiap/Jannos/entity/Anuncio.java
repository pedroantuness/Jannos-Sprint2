package br.com.fiap.Jannos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "T_GOANDGETIT_ANUNCIO")
public class Anuncio {

    @Id
    @SequenceGenerator(name = "SQ_ANUNCIO", sequenceName = "SQ_ANUNCIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ANUNCIO")
    @Column(name = "ID_ANUNCIO")
    private Long id;

    @Column(name = "VL_ANUNCIO")
    private Double valor;

    @Column(name = "QT_ANUNCIO")
    private Integer quantidade;

    @Column(name = "DS_ANUNCIO")
    private String descricao;

    @Column(name = "PRAZO_ANUNCIO")
    private String prazo;

    @Column(name = "DT_INICIO")
    private Date inicio;

    @Column(name = "DT_FIM")
    private Date fim;

}
