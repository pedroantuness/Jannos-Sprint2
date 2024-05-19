package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record ConsumoResponse(

        Long id,
        String quantidade,
        String descricao,
        ClienteResponse cliente,
        AnuncioResponse anuncio

) {
}
