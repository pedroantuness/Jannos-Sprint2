package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record ProdutoResponse(

        Long id,
        String nome,
        String descricao,
        String tipo,
        AnuncioResponse anuncio,
        LojaResponse loja

) {
}
