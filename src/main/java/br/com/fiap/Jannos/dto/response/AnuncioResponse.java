package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record AnuncioResponse(

        Long id,
        Double valor,
        Integer quantidade,
        String descricao,
        String prazo,
        Date inicio,
        Date fim
) {
}
