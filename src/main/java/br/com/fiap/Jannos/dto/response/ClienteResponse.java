package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record ClienteResponse(

        Long id,
        String nome,
        String email,
        String cpf,
        EnderecoResponse endereco,
        NotaResponse nota

) {
}
