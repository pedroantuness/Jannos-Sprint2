package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record NotaResponse(

        Long id,
        Double valor,
        String media,
        String descricao

) {
}
