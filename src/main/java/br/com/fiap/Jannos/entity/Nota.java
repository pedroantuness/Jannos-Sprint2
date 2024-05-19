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
@Table(name = "T_GOANDGETIT_NOTA")
public class Nota {

    @Id
    @SequenceGenerator(name = "SQ_NOTA", sequenceName = "SQ_NOTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NOTA")
    @Column(name = "ID_NOTA")
    private Long id;

    @Column(name = "VL_NOTA")
    private Double valor;

    @Column(name = "MEDIA_NOTA")
    private String media;

    @Column(name = "DS_NOTA")
    private String descricao;

}
