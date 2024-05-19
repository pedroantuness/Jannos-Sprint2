package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record LojaResponse(

        Long id,
        String nome,
        String tipo,
        String descricao,
        EnderecoResponse endereco,
        NotaResponse nota

) {
}
